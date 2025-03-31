package com.btc.api.dao;

import org.springframework.stereotype.Repository;

@Repository
public class BlockDAO extends GenericDAO {

    public Integer maxHeight() {
        return (Integer) em.createQuery(
                        "select max(height) from Block")
                .getResultList().stream().findFirst().orElse(0);
    }

}
