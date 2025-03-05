package com.btc.api.services;

import com.btc.api.model.Price;
import com.btc.model.Occurrence;
import com.btc.model.SystemInput;
import com.btc.model.SystemResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.Calendar;
import java.util.stream.Collectors;

import static com.btc.api.messages.Responses.*;
import static com.btc.utils.Utils.roundTo2Decimals;

@Service
public class SystemService {

    @Autowired
    protected PriceService priceService;

    public SystemResult getSystemResult(SystemInput systemInput) throws Exception {
        List<Price> priceList = priceService.getFullList();

        SystemResult systemResult = new SystemResult();

        // filter priceList by from / to

        Date from = systemInput.getFrom();
        Date to = systemInput.getTo();
        try {
            if (from == null) {
                String date_string = "2009-01-01";
                //Instantiating the SimpleDateFormat class
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                //Parsing the given String to Date object
                from = formatter.parse(date_string);
            }

            if (to == null) {
                String date_string = "9999-12-31";
                //Instantiating the SimpleDateFormat class
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                //Parsing the given String to Date object
                to = formatter.parse(date_string);
            }
        } catch (Exception e) {
            throw new Exception("from and to dates are wrong");
        }

        // from is after to
        if (from.compareTo(to) >= 0) {
            throw new Exception(FROM_TO_DATES_WRONG);
        }

        int target = systemInput.getTarget();
        if (target == 0) {
            target = 1;
        }
        if (target < 0) {
            throw new Exception(NEGATIVE_TARGET);
        }
        systemResult.setTarget(target);

        Calendar cal = Calendar.getInstance();
        cal.setTime(to);
        // use add() method to add the days to the given date
        cal.add(Calendar.DAY_OF_MONTH, target);

        List<Price> priceFilteredByDate = priceList.stream()
                .filter(getPricePredicate(from, cal.getTime()))
                .collect(Collectors.toCollection(ArrayList::new)); // mutable list

        double firstPrice = priceFilteredByDate.get(0).getPrice();
        double change = priceFilteredByDate.get(0).getChg();
        double priceBeforeTheFirstOne = firstPrice / (change / 100 + 1);
        priceFilteredByDate.add(0, new Price(0L, "", priceBeforeTheFirstOne, 0.0, 0.0, 0.0, 0.0));

        // check params and set default values : upDown, streak, amount, timespan -> result is a list of prices that match the conditions

        int streak = systemInput.getStreak();
        if (streak == 0) {
            streak = 1;  // default is streak of 1
        }

        if (streak < 0) {
            throw new Exception(NEGATIVE_STREAK);
        }

        int timespan = systemInput.getTimespan();
        if (timespan == 0) {
            timespan = 1;
        }
        if (timespan < 0) {
            throw new Exception(NEGATIVE_TIMESPAN);
        }
        if (timespan > 365) {
            throw new Exception(TIMESPAN_TOO_HIGH);
        }

        Double min = systemInput.getMin();
        Double max = systemInput.getMax();

        if (min == null && max == null) {
            throw new Exception(MIN_MAX_NOT_PRESENT);
        }

        if (min == null) {
            min = -100.0;
        }

        if (max == null) {
            max = 1000.0;
        }

        if (min > max) {
            throw new Exception(MIN_HIGHER_THAN_MAX);
        }

        List<Price> pricesThatMatchConditions = new ArrayList<>();
        List<Price> pricesAtTarget = new ArrayList<>();

        for (int i = 0; i < priceFilteredByDate.size() - streak * timespan - 1; i++) {
            if (applyConditions(min, max, priceFilteredByDate, i, streak, timespan)) {
                pricesThatMatchConditions.add(priceFilteredByDate.get(i + timespan * streak));
                if (i + streak * timespan + target < priceFilteredByDate.size()) {
                    pricesAtTarget.add(priceFilteredByDate.get(i + streak * timespan + target));
                } else {
                    pricesAtTarget.add(priceFilteredByDate.get(priceFilteredByDate.size() - 1));
                }
            }
        }
        // no matching prices
        if (pricesThatMatchConditions.isEmpty()) {
            return null;
        }

        List<Occurrence> occurrencesList = new ArrayList<>();

        // aggregate results and return them in the systemResult object
        int upOccurrences = 0;
        int downOccurrences = 0;
        double overallUp = 0.0;
        double overallDown = 0.0;
        double overallROI = 0.0;
        for (int i = 0; i < pricesAtTarget.size(); i++) {
            Double priceAtCondition = pricesThatMatchConditions.get(i).getPrice();
            Double priceAtTarget = pricesAtTarget.get(i).getPrice();
            System.out.println("priceAtCondition " + priceAtCondition);
            System.out.println("priceAtTarget " + priceAtTarget);
            if (priceAtTarget >= priceAtCondition) {
                upOccurrences++;
                overallUp += priceAtTarget - priceAtCondition;
            } else {
                downOccurrences++;
                overallDown += priceAtTarget - priceAtCondition;
            }
            overallROI += priceAtTarget / priceAtCondition;
            occurrencesList.add(new Occurrence(priceAtCondition, priceAtTarget, pricesThatMatchConditions.get(i).getDate()));
        }

        int size = pricesAtTarget.size();
        if ("full".equalsIgnoreCase(systemInput.getMode())) {
            systemResult.setOccurrenceList(occurrencesList);
        }
        systemResult.setNrOccurrences(size);
        systemResult.setUpOccurrences(upOccurrences);
        systemResult.setDownOccurrences(downOccurrences);

        systemResult.setAverageDown(Math.round(overallDown / downOccurrences));
        systemResult.setAverageUp(Math.round(overallUp / upOccurrences));

        systemResult.setOverall(Math.round(overallUp + overallDown));
        systemResult.setRoi(roundTo2Decimals((overallROI / size - 1) * 100));
        System.out.println("Finished system calculation");
        return systemResult;
    }

    private boolean applyConditions(double min, double max, List<Price> prices, int index, int streak, int timespan) {

        int j = index;
        while (streak > 0 && j + timespan < prices.size()) {
            streak--;
            Price startPrice = prices.get(j);
            Price endPrice = prices.get(j + timespan);
            double delta = endPrice.getPrice() - startPrice.getPrice();
            double change = delta / startPrice.getPrice() * 100;
            if (change < min || change > max) {
                return false;
            }
            j += timespan;
        }

        return true;
    }

    private static @NotNull Predicate<Price> getPricePredicate(Date from, Date to) {
        return p -> {
            try {
                java.util.Date parse = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(p.getDate());
                return parse.toString().equals(from.toString()) || parse.after(from) && parse.before(to);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        };
    }

}
