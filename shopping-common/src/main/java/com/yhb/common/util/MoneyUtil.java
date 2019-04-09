package com.yhb.common.util;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/3/8 10:55
 * @Description:
 */
public class MoneyUtil {
    public MoneyUtil() {
    }

    public static Long caculateFee(Long amount, Long feeRate) {
        return amount != null && amount >= 1L && feeRate != null && feeRate >= 1L ? amount * feeRate / 10000L : 0L;
    }

    public static String fen2Yuan(Long fen) {
        if (fen == null) {
            return "0";
        } else {
            double dbFen = (double)fen;
            return dbFen % 100.0D != 0.0D ? String.valueOf(dbFen / 100.0D) : String.valueOf(fen / 100L);
        }
    }

    public static long yuan2Fen(String yuan) {
        Double.valueOf(yuan.trim());
        int indexPoint = yuan.indexOf(".");
        if (indexPoint == -1) {
            return Long.parseLong(yuan + "00");
        } else if (indexPoint == 0) {
            return yuan.length() == 2 ? Long.parseLong(yuan.substring(1, 2) + "0") : Long.parseLong(yuan.substring(1, 3));
        } else if (yuan.length() == indexPoint + 1) {
            return Long.parseLong(yuan.substring(0, indexPoint) + "00");
        } else {
            String decimalValue = yuan.substring(indexPoint + 1);
            return decimalValue.length() == 1 ? Long.parseLong(yuan.substring(0, indexPoint) + yuan.substring(indexPoint + 1, indexPoint + 2) + "0") : Long.parseLong(yuan.substring(0, indexPoint) + yuan.substring(indexPoint + 1, indexPoint + 3));
        }
    }
}
