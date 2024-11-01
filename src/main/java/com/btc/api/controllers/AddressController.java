package com.btc.api.controllers;

import com.btc.api.model.Address;
import com.btc.api.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/address") //
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping(value = "/list")
    public List<Address> list() {
        return addressService.list();
    }

    @GetMapping(value = "/count")
    public int count() {
        return addressService.count();
    }

    @GetMapping(value = "/{address}")
    public Address getAddress(@PathVariable(value = "address") String address) {
        return addressService.getAddress(address);
    }

    @GetMapping(value = "/like/{chunk}")
    public List<Address> getAddressesLike(@PathVariable(value = "chunk") String chunk) {
        return addressService.getAddressesLike(chunk);
    }
}
