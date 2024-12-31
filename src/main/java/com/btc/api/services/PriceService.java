package com.btc.api.services;

import com.btc.api.dao.PriceDAO;
import com.btc.api.model.Price;
import com.btc.model.PriceStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceService {

    @Autowired
    protected PriceDAO priceDAO;

    public Price getPriceAt(String date) {
        Price priceAt = priceDAO.getPriceAt(date);
        return priceAt;
    }

    public PriceStats getPriceStats() {
        List<Price> list = priceDAO.list(Price.class);
        List<Double> prices = list.stream().map(Price::getPrice).collect(Collectors.toList());
        Collections.sort(prices);
        Price maxChange = list.stream().max(Comparator.comparing(Price::getChg)).get();
        Price minChange = list.stream().min(Comparator.comparing(Price::getChg)).get();
        Price athPrice = list.stream().max(Comparator.comparing(Price::getPrice)).get();

        PriceStats priceStats = new PriceStats();
        double average = prices.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
        average = average * 100.0 / 100.0; // 2 decimals
        long upDays = list.stream().filter(p -> p.getChg() >= 0).count();
        long downDays = list.size() - upDays;

        double median = prices.get(prices.size() / 2);
        double ath = athPrice.getPrice();

        int daysSinceATH = (int) (list.get(list.size() - 1).getId() - athPrice.getId());
        priceStats.setMedianPrice(median);
        priceStats.setAveragePrice(average);
        priceStats.setDownDays((int) downDays);
        priceStats.setUpDays((int) upDays);
        priceStats.setBiggestDecrease(minChange.toString());
        priceStats.setBiggestIncrease(maxChange.toString());
        priceStats.setAth(ath);
        priceStats.setDaysSinceATH(daysSinceATH);

        return priceStats;
    }

}
