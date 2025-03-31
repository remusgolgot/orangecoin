package com.btc.api.dao;

import org.springframework.stereotype.Repository;

@Repository
public class UtxoDAO extends GenericDAO {

    public Long countUnspentUtxo() {
        return (Long) em.createQuery(
                        "select count(*) from TransactionOutput u where spent = false")
                .getResultList().stream().findFirst().orElse(0);
    }

    public Long countUnspentUtxo(String address) {
        return (Long) em.createQuery(
                        "select count(*) from TransactionOutput u where spent = false AND address = '" + address + "'")
                .getResultList().stream().findFirst().orElse(0);
    }

    public Long countSpentUtxo(String address) {
        return (Long) em.createQuery(
                        "select count(*) from TransactionOutput u where spent = true AND address = '" + address + "'")
                .getResultList().stream().findFirst().orElse(0);
    }

    public Long getTotal() {
        return (Long) em.createQuery(
                        "SELECT SUM(value) FROM TransactionOutput where spent = false")
                .getResultList().stream().findFirst().orElse(0L);
    }

    public Long getUnspendableBalanceSum() {
        return (Long) em.createQuery(
                        "SELECT sum(value) AS sum FROM TransactionOutput where address is null")
                .getResultList().stream().findFirst().orElse(0L);
    }
}
