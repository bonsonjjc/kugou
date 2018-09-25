package com.kugou.player.manager

object Utils {
    fun minute(duration: Long): String {
        val temp = (duration / 1000).toInt()
        var minute = 0
        val second: Int

        if (temp < 60) {
            second = temp
        } else {
            minute = temp / 60
            second = temp % 60
        }
        return String.format("%2d:%02d", minute, second)
    }

    fun minute(duration: Int): String {
        var minute = 0
        val second: Int

        if (duration < 60) {
            second = duration
        } else {
            minute = duration / 60
            second = duration % 60
        }
        return String.format("%2d:%02d", minute, second)
    }
}