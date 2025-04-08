package com.btc.api.controllers;

import com.btc.api.model.ResponseModel;
import com.btc.api.services.AddressService;
import com.btc.model.AddressDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController extends BaseController {

    @Autowired
    AddressService addressService;

    /**
     * @return number of addresses in the DB
     */
    @GetMapping(value = "/count")
    public long count() {
        return addressService.count();
    }

    /**
     *
     * @return limited information related to the given address
     */
    @GetMapping(value = "/{address}")
    public ResponseModel<AddressDto> getAddress(@PathVariable(value = "address") String address) {
        AddressDto result = addressService.getAddress(address);
        return getResponseModel(result);
    }

    /**
     *
     * @return full information related to the given address
     */
    @GetMapping(value = "/{address}/full")
    public ResponseModel<AddressDto> getAddressFull(@PathVariable(value = "address") String address) {
        AddressDto result = addressService.getAddressFull(address);
        return getResponseModel(result);
    }

    /**
     *
     * @return all addresses that contain the chunk (as a string) in their name
     */
    @GetMapping(value = "/like/{chunk}")
    public ResponseModel<List<AddressDto>> getAddressesLike(@PathVariable(value = "chunk") String chunk) {
        List<AddressDto> addressesLike = addressService.getAddressesLike(chunk);
        return getListResponseModel(addressesLike);
    }

    /**
     *
     * @return all addresses tagged with a meta information
     */
    @GetMapping(value = "/meta")
    public ResponseModel<List<AddressDto>> getAddressesMeta() {
        List<AddressDto> addressesMeta = addressService.getAddressesMeta();
        return getListResponseModel(addressesMeta);
    }

    /**
     * @return top 100 addresses (by balance)
     */
    @GetMapping(value = "/top")
    public ResponseModel<List<AddressDto>> getAddressesTop() {
        List<AddressDto> addressesTop = addressService.getAddressesTop(100);
        return getListResponseModel(addressesTop);
    }

    /**
     * @return top addresses (number determines how many results)
     */
    @GetMapping(value = "/top/{number}")
    public ResponseModel<List<AddressDto>> getAddressesTop(@PathVariable(value = "number") int number) {
        List<AddressDto> addressesTop = addressService.getAddressesTop(number);
        return getListResponseModel(addressesTop);
    }

    /**
     * @return the addresses with a balance higher than the given amount
     */
    @GetMapping(value = "/greater/{amount}")
    public ResponseModel<List<AddressDto>> getAddressesGreater(@PathVariable(value = "amount") int amount) {
        try {
            List<AddressDto> addressesGreater = addressService.getAddressesGreater(amount);
            return getListResponseModel(addressesGreater);
        } catch (Exception e) {
            ResponseModel<List<AddressDto>> responseModel = getListResponseModel(null);
            responseModel.setError(e.getMessage());
            return responseModel;
        }
    }

}
