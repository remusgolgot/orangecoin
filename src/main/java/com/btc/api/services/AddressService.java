package com.btc.api.services;

import com.btc.api.dao.AddressDAO;
import com.btc.api.dao.GenericDAO;
import com.btc.api.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    protected AddressDAO addressDAO;

    @Autowired
    protected GenericDAO genericDAO;

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
}
