package com.btc.api.dao;

import com.btc.api.model.Address;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressDAO extends GenericDAO {

    public Address getAddress(String address) {
        return (Address) em.createQuery(
                        "SELECT a FROM Address a where address = '" + address + "'")
                .getResultList().stream().findFirst().orElse(null);
    }

    public List<Address> getAddressesLike(String chunk) {
        return (List<Address>) em.createQuery(
                        "SELECT a FROM Address a where address like '%" + chunk + "%'")
                .getResultList();
    }

    public List<Address> getAddressesTop(int number) {
        return (List<Address>) em.createQuery(
                        "SELECT a FROM Address a order by balance desc limit " + number)
                .getResultList();
    }

    public List<Address> getAddressesMeta() {
        return (List<Address>) em.createQuery(
                        "SELECT a FROM Address a where meta != ''")
                .getResultList();
    }

    public List<Address> getAddressesGreater(long amount) {
        return (List<Address>) em.createQuery(
                        "SELECT a FROM Address a where balance > " + amount)
                .getResultList();
    }

    public Double getTotalBalance() {
        return (Double) em.createQuery(
                        "SELECT SUM(balance) FROM Address")
                .getResultList().stream().findFirst().orElse(0.0);
    }

    public Long getNrNonEmptyAddresses() {
        return (Long) em.createQuery(
                        "select count(*) from Address a where balance > 0")
                .getResultList().stream().findFirst().orElse(0);
    }

    public Double getSpentZeroAddressesSum() {
        return (Double) em.createQuery(
                        "SELECT sum(balance) FROM Address WHERE balance = received")
                .getResultList().stream().findFirst().orElse(0.0);
    }

    public Double getLowSpentAddressesSum() {
        return (Double) em.createQuery(
                        "SELECT sum(balance) AS sum FROM Address where (received-balance)/received < 0.1")
                .getResultList().stream().findFirst().orElse(0.0);
    }
}
