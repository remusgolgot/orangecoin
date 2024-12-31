package com.btc.api.controllers;

import com.btc.api.model.Price;
import com.btc.api.model.ResponseModel;
import com.btc.api.services.PriceService;
import com.btc.model.PriceStats;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prices") //
public class PriceController {

    @Autowired
    PriceService priceService;

    /**
     * @return price stats
     */
    @GetMapping(value = "/stats")
    public ResponseModel<PriceStats> getStats() {
        PriceStats priceStats = priceService.getPriceStats();
        return getResponseModel(priceStats);
    }

    /**
     *
     * @param date
     * @return the price at the given date
     */
    @GetMapping(value = "/{date}")
    public ResponseModel<Price> getPriceAt(@PathVariable(value = "date") String date) {
        Price result = priceService.getPriceAt(date + " 00:00:00 UTC");
        return getResponseModel(result);
    }

    private static @NotNull <T> ResponseModel<T> getResponseModel(T entity) {
        ResponseModel<T> response = new ResponseModel<>();
        response.setData(entity);
        response.setStatus(entity != null);
        response.setCount(entity != null ? 1 : 0);
        response.setMessage(entity == null ? "No result found" : "");
        return response;
    }
}
