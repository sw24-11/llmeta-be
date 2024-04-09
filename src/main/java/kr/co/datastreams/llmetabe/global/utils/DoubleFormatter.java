package kr.co.datastreams.llmetabe.global.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleFormatter {
    public static Double format(Double num) {
        if(num == null) return null;
        BigDecimal bd = new BigDecimal(num).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}