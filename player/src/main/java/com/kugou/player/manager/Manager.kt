package com.kugou.player.manager

import com.kugou.audio.model.Music

class PlayBack {
    internal var playMusic: Music? = null

    var playIndex: Int = 0

    internal var playProgress: Int = 0

    val playDuration: Int get() = playMusic?.duration?.toInt() ?: 0

    internal var playMode: PlayMode = PlayMode.MODE_ORDER
}

enum class PlayMode {
    MODE_ORDER, MODE_RANDOM, MODE_LOOP
}

object PlayAction {
    val PLAY_ACTION_PLAY = 10
    val PLAY_ACTION_PAUSE = 11
    val PLAY_ACTION_STOP = 12
    val PLAY_ACTION_BUFFER_START = 13
    val PLAY_ACTION_BUFFER_END = 14

    val PLAY_ACTION_LIST_CHANGE = 21
    val PLAY_ACTION_NEXT = 22
    val PLAY_ACTION_PREVIOUS = 23
    val PLAY_ACTION_PROGRESS = 24
    val PLAY_ACTION_MUSIC = 25
    val PLAY_ACTION_BUFFER_PERCENT = 26
    val PLAY_ACTION_COMPLETION = 27
    val PLAY_ACTION_PREPARED = 28
}