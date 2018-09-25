package com.kugou.view.ui.fragment.netmusic.singer.songs

import androidx.lifecycle.MutableLiveData
import com.kugou.audio.model.Music
import com.kugou.commom.BaseViewModel
import com.kugou.player.PlayerBackUtils
import com.kugou.repository.netmusic.music.MusicServer
import io.reactivex.android.schedulers.AndroidSchedulers

open class SongViewModel : BaseViewModel() {
    private val musicServer: MusicServer = MusicServer()
    val dataList = MutableLiveData<List<Music>>()

    var page: Int = 1
    var pageSize: Int = 30

    fun list(url: String) {
        musicServer.songList(url, page, pageSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.status == 1) {
                        dataList.value = it.data!!.info
                    }
                }, {
                    it.printStackTrace()
                })
    }

    fun play(position: Int) {
        PlayerBackUtils.play(position, dataList.value!!)
    }
}