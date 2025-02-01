package com.btc.api.services;

import com.btc.api.model.Price;
import com.btc.model.SystemInput;
import com.btc.model.SystemResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

@Service
public class SystemService {

    @Autowired
    protected PriceService priceService;

    public SystemResult getSystemResult(SystemInput systemInput) {
        List<Price> priceList = priceService.getFullList();

        SystemResult systemResult = new SystemResult();

        // filter priceList by from / to

        Date from = systemInput.getFrom();
        Date to = systemInput.getTo();

        List<Price> priceFilteredByDate = priceList.stream()
                .filter(getPricePredicate(from, to))
                .toList();

        // check params : upDown, streak, amount, timespan -> result is a list of prices that match the conditions

        int timespan = systemInput.getTimespan();
        if (timespan == 0) {
            timespan = 1;
        }
        systemResult.setTimespan(timespan);

        int streak = systemInput.getStreak();
        if (streak == 0) {
            streak = 1;  // default is streak of 1
        }

        double upDown = systemInput.getUpOrDown();
        if (upDown == 0) {
            upDown = 1; // default is up (1)
        }

        double amount = systemInput.getAmount();
        if (amount == 0.0) {
            if (upDown == 1) {
                amount = 0.01;
            } else {
                amount = -0.01;
            }
        }

        List<Price> pricesThatMatchConditions = new ArrayList<>();
        List<Price> pricesWithTimespan = new ArrayList<>();

        for (int i = 0; i < priceFilteredByDate.size() - streak; i++) {
            if (applyConditions(amount, upDown, priceFilteredByDate.subList(i, i + streak + 1))) {
                pricesThatMatchConditions.add(priceFilteredByDate.get(i + streak));
                if (i + streak + timespan < priceFilteredByDate.size()) {
                    pricesWithTimespan.add(priceFilteredByDate.get(i + streak + timespan));
                } else {
                    pricesWithTimespan.add(priceFilteredByDate.get(priceFilteredByDate.size() - 1));
                }
            }
        }

        // aggregate results and return them in the systemResult object
        int upOccurrences = 0;
        int downOccurrences = 0;
        double overallUp = 0.0;
        double overallDown = 0.0;
        double overallROI = 0.0;
        for (int i = 0; i < pricesWithTimespan.size(); i++) {
            Double priceAfter = pricesWithTimespan.get(i).getPrice();
            Double priceAtCondition = pricesThatMatchConditions.get(i).getPrice();
            System.out.println(priceAfter);
            System.out.println(priceAtCondition);
            System.out.println();
            if (priceAfter >= priceAtCondition) {
                upOccurrences++;
                overallUp += priceAfter - priceAtCondition;
                overallROI += priceAfter / priceAtCondition;
            } else {
                downOccurrences++;
                overallDown += priceAfter - priceAtCondition;
                overallROI += priceAfter / priceAtCondition;
            }
            System.out.println(overallROI);
        }

        int size = pricesWithTimespan.size();
        systemResult.setNrOccurrences(size);
        systemResult.setUpOccurrences(upOccurrences);
        systemResult.setDownOccurrences(downOccurrences);

        systemResult.setAverageDown(Math.round(overallDown / downOccurrences));
        systemResult.setAverageUp(Math.round(overallUp / upOccurrences));

        systemResult.setOverall(Math.round(overallUp + overallDown));
        systemResult.setRoi((overallROI / size - 1) * 100);
        System.out.println("Finished system calculation");
        return systemResult;
    }

    private boolean applyConditions(double amount, double upDown, List<Price> prices) {
        if (amount < 0 && upDown == 1) {
            return false;
        }
        if (amount > 0 && upDown == -1) {
            return false;
        }
        for (int i = 0; i < prices.size() - 1; i++) {
            for (int j = i + 1; j < prices.size(); j++) {
                if (prices.get(j).getChg() <= amount && upDown == 1) {
                    return false;
                }
                if (prices.get(j).getChg() >= amount && upDown == -1) {
                    return false;
                }
            }
        }

        return true;
    }

    private static @NotNull Predicate<Price> getPricePredicate(Date from, Date to) {
        return p -> {
            if (from == null || to == null) {
                return true;
            }
            try {
                int f = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(p.getDate()).compareTo(from);
                int t = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(p.getDate()).compareTo(to);
                return f >= 0 && t <= 0;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        };
    }

}
