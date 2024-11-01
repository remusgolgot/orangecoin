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
}
