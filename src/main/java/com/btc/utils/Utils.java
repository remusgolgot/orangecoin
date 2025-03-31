package com.btc.utils;

import com.btc.api.model.Address;
import com.btc.model.AddressDto;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String prettyBalance(Double balance) {
        balance = balance / 100000000;
        DecimalFormat df = new DecimalFormat("#.########");
        df.setMaximumFractionDigits(8);
        return df.format(balance);
    }

    public static double roundTo2Decimals(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

    public static double roundToNDecimals(double value, int nrDecimals) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(nrDecimals, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

    public static String longTimeStampToDateString(long timestamp) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(timestamp * 1000);
    }

    public static AddressDto transformAddress(Address address1) {
        AddressDto address2 = new AddressDto();
        BeanUtils.copyProperties(address1, address2);
        address2.setBalance(address2.getBalance() / 100000000.0);
        address2.setReceived(address2.getReceived() / 100000000.0);
        return address2;
    }

    public static List<AddressDto> transformAddresses(List<Address> addresses) {
        List<AddressDto> result = new ArrayList<>();
        for (Address address : addresses) {
            result.add(transformAddress(address));
        }
        return result;
    }
}
