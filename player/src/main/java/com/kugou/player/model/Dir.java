package com.kugou.player.model;

import android.os.Environment;

public class Dir {

    private static String sdPath;

    static {
        sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/kuku";
        FileUtils.makeDir(sdPath);
    }

    public static final String image = sdPath + "/cache/image/singer/";
    public static final String music = sdPath + "/cache/music/";
    public static final String mv_download = sdPath + "/download/mv";

    public static String music_download = sdPath + "/download/music";

}
