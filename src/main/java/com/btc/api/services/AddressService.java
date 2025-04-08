package com.btc.api.services;

import com.btc.api.dao.AddressDAO;
import com.btc.api.dao.BlockDAO;
import com.btc.api.dao.UtxoDAO;
import com.btc.api.model.Address;
import com.btc.model.AddressDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.btc.utils.Utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.btc.api.messages.Responses.AMOUNT_IS_GREATER_THAN_MAX_SUPPLY;
import static com.btc.api.messages.Responses.AMOUNT_IS_LOWER_THAN_ZERO;

@Service
public class AddressService implements Cloneable {

    @Autowired
    protected AddressDAO addressDAO;

    @Autowired
    protected UtxoDAO utxoDAO;

    @Autowired
    protected BlockDAO blockDAO;

    public long count() {
        return addressDAO.count(Address.class);
    }

    public List<Address> list() {
        return addressDAO.list(Address.class);
    }

    public AddressDto getAddressFull(String address) {
        Address address1 = addressDAO.getAddress(address);
        if (address1 == null) {
            return null;
        }

        AddressDto addressDto = Utils.transformAddress(address1);

        Long unspent = utxoDAO.countUnspentUtxo(addressDto.getAddress());
        Long spent = utxoDAO.countSpentUtxo(addressDto.getAddress());

        long firstInputTimestamp = addressDto.getFirstInput() * 1000;
        long age = System.currentTimeMillis() - firstInputTimestamp;
        long ageInDays = TimeUnit.MILLISECONDS.toDays(age);

        Long lastInput = addressDto.getLastInput();
        long lastInputTimestamp = lastInput != null ? lastInput * 1000 : 0;

        Long lastOutput = addressDto.getLastOutput();
        long lastOutputTimestamp = lastOutput != null ? lastOutput * 1000 : 0;
        long dormancy = age;
        long max = Math.max(lastInputTimestamp, lastOutputTimestamp);
        if (max > 0) {
            dormancy = System.currentTimeMillis() - max;
        }
        long dormancyInDays = TimeUnit.MILLISECONDS.toDays(dormancy);

        addressDto.setAge(ageInDays);
        addressDto.setDormancy(dormancyInDays);
        addressDto.setToxicity(0L);
        addressDto.setUxtoUnSpent(unspent);
        addressDto.setUxtoSpent(spent);
        addressDto.setUxto(spent + unspent);
        return addressDto;
    }

    public AddressDto getAddress(String address) {
        Address address1 = addressDAO.getAddress(address);
        return address1 != null ? Utils.transformAddress(address1) : null;
    }

    public List<AddressDto> getAddressesLike(String chunk) {
        List<Address> addressesLike = addressDAO.getAddressesLike(chunk);
        return Utils.transformAddresses(addressesLike);
    }

    public List<AddressDto> getAddressesTop(int number) {
        List<Address> addressesTop = addressDAO.getAddressesTop(number);
        return Utils.transformAddresses(addressesTop);
    }

    public List<AddressDto> getAddressesMeta() {
        List<Address> addressesMeta = addressDAO.getAddressesMeta();
        return Utils.transformAddresses(addressesMeta);
    }

    public List<AddressDto> getAddressesGreater(int amount) throws Exception {
        if (amount > 21000000) {
            throw new Exception(AMOUNT_IS_GREATER_THAN_MAX_SUPPLY);
        }
        if (amount < 0) {
            throw new Exception(AMOUNT_IS_LOWER_THAN_ZERO);
        }
        long x = amount * 100000000L;
        List<Address> addressesGreater = addressDAO.getAddressesGreater(x);
        return Utils.transformAddresses(addressesGreater);
    }

    public long getAddressesNotEmpty() {
        return addressDAO.getNrNonEmptyAddresses();
    }

    public double getSpentZeroAddressesSum() {
        return addressDAO.getSpentZeroAddressesSum();
    }

    public double getTotalBalanceSum() {
        return addressDAO.getTotalBalance();
    }

    public double getLowSpentAddressesSum() {
        return addressDAO.getLowSpentAddressesSum();
    }

    public long getTotalAddresses() {
        return addressDAO.count(Address.class);
    }

    public int getMaxHeight() {
        return blockDAO.maxHeight();
    }
}
