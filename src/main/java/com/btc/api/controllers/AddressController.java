package com.btc.api.controllers;

import com.btc.api.model.ResponseModel;
import com.btc.api.services.AddressService;
import com.btc.api.services.UtxoService;
import com.btc.model.AddressDto;
import com.btc.model.SummaryDto;
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

    @Autowired
    UtxoService utxoService;

    /**
     *
     * @return summary information
     */
    @GetMapping(value = "/summary")
    public ResponseModel<SummaryDto> getSummary() {

        SummaryDto summaryDto = new SummaryDto();

        long totalAddresses = addressService.getTotalAddresses();
        long nrNonEmptyAddresses = addressService.getAddressesNotEmpty();
        double totalBalance = addressService.getTotalBalanceSum();
        double lowSpentAddressesSum = addressService.getLowSpentAddressesSum();
        double spentZeroAddressesSum = addressService.getSpentZeroAddressesSum();
        int maxHeight = addressService.getMaxHeight();
        double unspendable = utxoService.getUnspendableBalanceSum();
        long totalUtxoSum = utxoService.getTotal();

        summaryDto.setTotalAddresses(totalAddresses);
        summaryDto.setNonEmptyAddresses(nrNonEmptyAddresses);
        summaryDto.setTotalBalance(totalBalance / 100000000.0);
        summaryDto.setLowSpentBalance(lowSpentAddressesSum / 100000000.0);
        summaryDto.setSpentZeroBalance(spentZeroAddressesSum / 100000000.0);
        summaryDto.setUnspendable(unspendable / 100000000.0);

        summaryDto.setNrUtxos(utxoService.count());
        summaryDto.setNrUnspentUtxos(utxoService.countUnspent());
        summaryDto.setUtxoTotal(totalUtxoSum);
        summaryDto.setMaxHeight(maxHeight);
        return getResponseModel(summaryDto);
    }

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


    // disable for now
//    @GetMapping(value = "/list")
//    public List<Address> list() {
//        return addressService.list();
//    }
}
