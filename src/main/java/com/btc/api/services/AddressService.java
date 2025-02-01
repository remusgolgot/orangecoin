package com.btc.api.services;

import com.btc.api.dao.AddressDAO;
import com.btc.api.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService implements Cloneable {

    @Autowired
    protected AddressDAO addressDAO;

    public int count() {
        return addressDAO.count(Address.class);
    }

    public List<Address> list() {
        return addressDAO.list(Address.class);
    }

    public Address getAddress(String address) {
        return addressDAO.getAddress(address);
    }

    public List<Address> getAddressesLike(String chunk) {
        return addressDAO.getAddressesLike(chunk);
    }

    public List<Address> getAddressesTop(int number) {
        return addressDAO.getAddressesTop(number);
    }

    public List<Address> getAddressesMeta() {
        return addressDAO.getAddressesMeta();
    }

    public List<Address> getAddressesGreater(int amount) {
        return addressDAO.getAddressesGreater(amount);
    }
}
