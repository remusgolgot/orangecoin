package com.btc.api.dao;

import com.btc.api.model.Price;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PriceDAO extends GenericDAO {

    public Price getPriceAt(String date) {
        return (Price) em.createQuery(
                        "SELECT a FROM Price a where date = '" + date + "'")
                .getResultList().stream().findFirst().orElse(null);
    }

    public List<Price> list(String date) {
        return em.createQuery(
                        "SELECT a FROM Price a where date >= '" + date + "'")
                .getResultList();
    }

    public List<Price> list(String dateFrom, String dateTo) {
        return em.createQuery(
                        "SELECT a FROM Price a where date >= '" + dateFrom + "' AND date <= '" + dateTo + "'")
                .getResultList();
    }
}
