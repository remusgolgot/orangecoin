package com.btc.utils;

import com.btc.model.Price;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Utils {

    public static String prettyBalance(Double balance) {
        balance = balance / 100000000;
        DecimalFormat df = new DecimalFormat("#.########");
        df.setMaximumFractionDigits(8);
        return df.format(balance);
    }


}
