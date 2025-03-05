package com.btc.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

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

}
