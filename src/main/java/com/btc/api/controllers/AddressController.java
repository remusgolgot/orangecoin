package com.btc.api.controllers;

import com.btc.api.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address") //
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping(value = "/count")
    public Integer count() {
        return addressService.countAddresses();
    }
}
