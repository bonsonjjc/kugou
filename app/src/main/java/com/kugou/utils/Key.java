package com.kugou.utils;

import android.text.format.Time;

public class Key {
    public static final String mid = "248882481314299591952125499167454859886";
    public static final String appid = "1005";
    public static final String appkey = "OIlwieks28dk2k092lksi2UIkp";

    public static final String userId = "0";

    public static final String clientver = "8942";

    public static String clienttime() {
        Time time=new Time();
        time.setToNow();
        return String.valueOf(time.toMillis(false));
    }

    public static String imageKey() {
        return Md5.md5(appid + appkey + clientver + clienttime()).toLowerCase();
    }

    public static String playKey(String hash) {
        String format = "%skgcloudv2%s%s%s";
        return Md5.md5(String.format(format, hash, appid, mid, userId)).toLowerCase();
    }

    public static String mvKey(String hash) {
        return Md5.md5(hash + "kugoumvcloud").toLowerCase();
    }
}
