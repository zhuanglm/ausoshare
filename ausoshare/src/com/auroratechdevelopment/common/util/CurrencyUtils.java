package com.auroratechdevelopment.common.util;

import android.text.TextUtils;

import com.auroratechdevelopment.common.DebugLogUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Edward on 2015-09-16.
 */
public class CurrencyUtils {

    public static BigDecimal convertFromString(String amount) {
        BigDecimal result = new BigDecimal("0");
        if (!TextUtils.isEmpty(amount)) {
            result = new BigDecimal(amount);
        }
        result.setScale(2, BigDecimal.ROUND_HALF_UP);
        return result;
    }

    public static String convertToStringWithFormat(BigDecimal amount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount);
    }

    public static String convertToString(BigDecimal amount) {
        return amount.toString();
    }

    public static String convertToCurrencyStringFromInt100(int amount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount/100);
    }

    public static int convertStringToInt100(String price) {
        if (TextUtils.isEmpty(price))
            return 0;

        BigDecimal amount = convertFromString(price);
        return (amount.multiply(CurrencyUtils.convertFromString(String.valueOf(100))).intValueExact());
    }

    public static String convertToStringFromInt100(int amount) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(2);
        return df.format(amount/100);
    }

    public static int convertFromStringToInt(String value) {
        int result = 0;
        try {
            result = Integer.parseInt(value);
        } catch(NumberFormatException e) {
            DebugLogUtil.Log(e, "convertFromString");
        }
        return result;
    }
}
