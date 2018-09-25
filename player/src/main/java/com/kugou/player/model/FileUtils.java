package com.kugou.player.model;

import java.io.File;

public class FileUtils {

    public static boolean isExists(String path) {
        File file = getFile(path);
        return file.exists() && file.isDirectory();
    }

    public static boolean isExists(File file) {
        return file.exists();
    }

    public static File getFile(String path) {
        return new File(path);
    }

    public static boolean makeDir(String path) {
        File file = getFile(path);
        return makeDir(file);
    }

    public static boolean makeDir(File file) {
        if (isExists(file)) {
            return true;
        }
        return file.mkdirs();
    }
}
