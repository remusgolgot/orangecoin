package com.btc.api.services;

import com.btc.api.dao.PriceDAO;
import com.btc.api.model.Price;
import com.btc.model.PriceStats;
import com.btc.utils.Utils;
import liquibase.pro.packaged.P;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceService {

    @Autowired
    protected PriceDAO priceDAO;

    public List<Price> getFullList() {
        return priceDAO.list(Price.class);
    }

    public Price getPriceAt(String date) {
        return priceDAO.getPriceAt(date);
    }

    public PriceStats getPriceStatsFrom(String date) {
        List<Price> fromList = priceDAO.list(date);
        return getPriceStats(fromList);
    }

    public PriceStats getPriceStatsFromTo(String dateFrom, String dateTo) {
        List<Price> fromList = priceDAO.list(dateFrom, dateTo);
        return getPriceStats(fromList);
    }

    public PriceStats getPriceStats() {
        List<Price> fullList = priceDAO.list(Price.class);
        return getPriceStats(fullList);
    }

    private PriceStats getPriceStats(List<Price> priceList) {
        PriceStats priceStats = new PriceStats();

        if (priceList.isEmpty()) {
            return priceStats;
        }

        double lastPrice = priceList.get(priceList.size() - 1).getPrice();

        List<Double> prices = priceList.stream().map(Price::getPrice).collect(Collectors.toList());
        Collections.sort(prices);
        Price maxChange = priceList.stream().max(Comparator.comparing(Price::getChg)).get();
        Price minChange = priceList.stream().min(Comparator.comparing(Price::getChg)).get();
        Price athPrice = priceList.stream().max(Comparator.comparing(Price::getPrice)).get();

        double average = prices.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
        average = average * 100.0 / 100.0; // 2 decimals
        long upDays = priceList.stream().filter(p -> p.getChg() >= 0).count();
        long downDays = priceList.size() - upDays;

        double median = prices.get(prices.size() / 2);
        double ath = athPrice.getPrice();

        int daysSinceATH = (int) (priceList.get(priceList.size() - 1).getId() - athPrice.getId());
        priceStats.setMedianPrice(median);
        priceStats.setAveragePrice(Utils.roundTo2Decimals(average));
        priceStats.setDownDays((int) downDays);
        priceStats.setUpDays((int) upDays);
        priceStats.setBiggestDecrease(minChange.toString());
        priceStats.setBiggestIncrease(maxChange.toString());
        priceStats.setAth(ath);
        priceStats.setDaysSinceATH(daysSinceATH);
        double athDrawdown = 100 * (1 - lastPrice / ath);
        athDrawdown = Utils.roundTo2Decimals(athDrawdown);
        priceStats.setAthDrawdown(athDrawdown);

        return priceStats;
    }

}
