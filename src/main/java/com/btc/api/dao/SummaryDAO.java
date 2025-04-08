package com.btc.api.dao;

import com.btc.api.model.Summary;
import org.springframework.stereotype.Repository;

@Repository
public class SummaryDAO extends GenericDAO {

    public Summary getSummary() {
        return (Summary) em.createQuery(
                        "SELECT a FROM Summary a")
                .getResultList().stream().findFirst().orElse(null);
    }
}
