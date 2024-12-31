package com.btc.api.controllers;

import com.btc.api.model.Address;
import com.btc.api.model.ResponseModel;
import com.btc.api.services.AddressService;
import org.jetbrains.annotations.NotNull;
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

    /**
     * @return number of addresses in the DB
     */
    @GetMapping(value = "/count")
    public int count() {
        return addressService.count();
    }

    /**
     *
     * @return the information related to the given the address
     */
    @GetMapping(value = "/{address}")
    public ResponseModel<Address> getAddress(@PathVariable(value = "address") String address) {
        Address result = addressService.getAddress(address);
        return getResponseModel(result);
    }

    /**
     *
     * @return all addresses that contain the chunk (as a string) in their name
     */
    @GetMapping(value = "/like/{chunk}")
    public ResponseModel<List<Address>> getAddressesLike(@PathVariable(value = "chunk") String chunk) {
        List<Address> addressesLike = addressService.getAddressesLike(chunk);
        return getListResponseModel(addressesLike);
    }

    /**
     *
     * @return all addresses tagged with a meta information
     */
    @GetMapping(value = "/meta")
    public ResponseModel<List<Address>> getAddressesMeta() {
        List<Address> addressesMeta = addressService.getAddressesMeta();
        return getListResponseModel(addressesMeta);
    }

    /**
     * @return top 100 addresses (by balance)
     */
    @GetMapping(value = "/top")
    public ResponseModel<List<Address>> getAddressesTop() {
        List<Address> addressesTop = addressService.getAddressesTop(100);
        return getListResponseModel(addressesTop);
    }

    /**
     * @return top addresses (number determines how many results)
     */
    @GetMapping(value = "/top/{number}")
    public ResponseModel<List<Address>> getAddressesTop(@PathVariable(value = "number") int number) {
        List<Address> addressesTop = addressService.getAddressesTop(number);
        return getListResponseModel(addressesTop);
    }

    /**
     * @return the addresses with a balance higher than the given amount
     */
    @GetMapping(value = "/greater/{amount}")
    public ResponseModel<List<Address>> getAddressesGreater(@PathVariable(value = "amount") int amount) {
        List<Address> addressesGreater = addressService.getAddressesGreater(amount);
        return getListResponseModel(addressesGreater);
    }

    private static @NotNull <T> ResponseModel<List<T>> getListResponseModel(List<T> list) {
        ResponseModel<List<T>> response = new ResponseModel<>();
        response.setData(list);
        response.setStatus(list != null && !list.isEmpty());
        response.setCount(list != null ? list.size() : 0);
        response.setMessage(list == null || list.isEmpty() ? "No results" : "");
        return response;
    }

    private static @NotNull <T> ResponseModel<T> getResponseModel(T entity) {
        ResponseModel<T> response = new ResponseModel<>();
        response.setData(entity);
        response.setStatus(entity != null);
        response.setCount(entity != null ? 1 : 0);
        response.setMessage(entity == null ? "No result found" : "");
        return response;
    }
}
