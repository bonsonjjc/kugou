package com.kugou.player.manager

import com.kugou.audio.model.Music
import java.util.*

class QueueManager {
    private var playList: MutableList<Music> = mutableListOf()

    fun add(list: List<Music>, start: Int = 0, isClear: Boolean = false) {
        if (isClear) {
            delete()
        }
        playList.addAll(start, list)
    }

    fun get(index: Int): Music? {
        if (playList.isNotEmpty() && index in 0..playList.size - 1) {
            return playList[index]
        }
        return null
    }

    fun count(): Int {
        if (playList.isEmpty()) {
            return 0
        }
        return playList.size
    }

    fun list(): List<Music> {
        return playList
    }

    fun move(form: Int, to: Int) {
        val fromMusic = playList[form]
        playList.removeAt(form)
        playList.add(to, fromMusic)
    }

    fun delete(index: Int = -1) {
        if (index == -1) {
            playList.clear()
        } else {
            playList.removeAt(index)
        }
    }

    val playBack = PlayBack()

    fun next() {
        val next = generate(mode_next)
        music(next)
    }

    fun previous() {
        val previous = generate(mode_provious)
        music(previous)
    }

    fun music(index: Int) {
        playBack.playIndex = index
        playBack.playMusic = playList[index]
        playBack.playProgress = 0
    }

    val mode_next = 0
    val mode_provious = 1

    private fun generate(mode: Int): Int {
        var index: Int
        when (playBack.playMode) {
            PlayMode.MODE_LOOP -> {
                return playBack.playIndex
            }
            PlayMode.MODE_ORDER -> {
                if (mode_next == mode) {
                    index = playBack.playIndex + 1
                    if (index >= count()) {
                        index = 0
                    }
                } else {
                    index = playBack.playIndex - 1
                    if (index < 0) {
                        index = playList.lastIndex
                    }
                }
            }
            else -> {
                index = random(count())
            }
        }
        return index
    }

    val random = Random()
    fun random(range: Int): Int {
        val nextInt = random.nextInt(range)
        return nextInt
    }
}