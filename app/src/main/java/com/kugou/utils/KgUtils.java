package com.kugou.utils;

import android.text.TextUtils;
import android.util.Pair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KgUtils {

    public static Pair<String, String> songs(String fileName, String remark) {
        String filename = fileName.replace("\\s", "");

        int star = filename.indexOf("-");
        String singer = filename.substring(0, star);
        int index = filename.indexOf("【");

        String temp = "";
        int end = index == -1 ? filename.length() : index;
        if (!TextUtils.isEmpty(remark)) {
            temp = "- " + remark;
        }
        String name = filename.substring(star + 2, end);
        return new Pair<>(name, singer);
    }

    public static Pair<String, String> bar(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return new Pair<>("酷狗音乐", "就是歌多");
        }
        String filename = fileName.replace("\\s", "");
        int star = filename.indexOf("-");
        int index = filename.indexOf("【");
        int end = index == -1 ? filename.length() : index;
        String singer = filename.substring(0, star);
        String name = filename.substring(star + 2, end);
        return new Pair<>(name, singer);
    }

    public static Pair<String, String> parseFileName(String fileName) {
        String filename = fileName.replace("\\s", "");

        int star = filename.indexOf("-");
        String singer = filename.substring(0, star);
        String name = filename.substring(star + 2);
        return new Pair<>(name, singer);
    }


    public static String date(String data) {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(data);
        boolean find = matcher.find();
        return find ? matcher.group() : "";
    }

    public static String toMinute(int duration) {
        int minute = 0;
        int second = (int) duration;
        if (duration >= 60) {
            minute = duration / 60;
            second = duration % 60;
        }
        return String.format("%d:%02d", minute, second);
    }


    public static String update(Date time) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(time) + " 更新";
    }

    public static String numberTo(int count) {
        if (count < 10000) {
            return count + "";
        }
        return String.format(Locale.CHINA, "%1.2f万", count / 10000f);
    }

    public static String heatFans(int fans, int heat) {
        return "热度:" + numberTo(heat) + ",粉丝数:" + numberTo(fans);
    }
}
