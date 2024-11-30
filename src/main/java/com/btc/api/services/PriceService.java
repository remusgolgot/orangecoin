package com.btc.api.services;

import com.btc.api.dao.AddressDAO;
import com.btc.api.dao.PriceDAO;
import com.btc.api.model.Address;
import com.btc.api.model.Price;
import com.btc.model.PriceStats;
import com.btc.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
        List<Double> prices = priceDAO.list(Price.class).stream().map(price -> price.getPrice()).collect(Collectors.toList());
        Collections.sort(prices);

        PriceStats priceStats = new PriceStats();
        double average = prices.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        double median = prices.get(prices.size() / 2);
        priceStats.setMedianPrice(median);
        priceStats.setAveragePrice(average);

        return priceStats;
    }

}
