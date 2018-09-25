package com.kugou.view.ui.fragment.netmusic.rank.week

import androidx.lifecycle.MutableLiveData
import com.kugou.audio.model.Music
import com.kugou.repository.netmusic.rank.RankServer
import com.kugou.repository.netmusic.rank.RankYear
import com.kugou.view.ui.fragment.netmusic.singer.songs.SongViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class RankSongViewModel : SongViewModel() {
    private val rankServer = RankServer()

    var songs = MutableLiveData<List<Music>>()

    var weekData = MutableLiveData<List<RankYear>>()

    var rankType: Int = 0
    var rankId: Int = 0
    var volid: Int? = 0

    var cycle = MutableLiveData<String>()

    fun init(rankType: Int, rankId: Int) {
        this.rankType = rankType
        this.rankId = rankId
    }

    fun rankCycle() {
        val disposable = rankServer.rankPeriods(rankType, rankId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.status == 1) {
                        val rankList = it.data!!.info!!
                        weekData.value = rankList
                        val week = rankList[0].vols!![0]
                        cycle.value = week.volname
                        list(week.volid)
                    }
                }, {
                    it.printStackTrace()
                })
    }

    fun list(volid: Int? = null) {
        this.volid = volid
        list("v3/rank/song?version=8983&ranktype=${rankType}&plat=0&area_code=1&volid=${volid}&rankid=${rankId}&with_res_tag=1")
    }
}