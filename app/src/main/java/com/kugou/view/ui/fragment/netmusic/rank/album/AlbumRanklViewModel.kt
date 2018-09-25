package com.kugou.view.ui.fragment.netmusic.rank.album

import androidx.lifecycle.MutableLiveData
import com.kugou.commom.BaseViewModel
import com.kugou.audio.model.Music
import com.kugou.repository.netmusic.rank.*
import io.reactivex.android.schedulers.AndroidSchedulers
import java.text.SimpleDateFormat
import java.util.*

class AlbumRanklViewModel : BaseViewModel() {
    private val rankServer = RankServer()

    lateinit var rankTag: RankTag

    val icon = MutableLiveData<String>()

    var title = MutableLiveData<String>()

    val updateTime = MutableLiveData<String>()

    val isVol = MutableLiveData<Int>()

    var songs = MutableLiveData<List<RankAlbum>>()
    var monthSongs = MutableLiveData<List<RankAlbum>>()
    var allSongs = MutableLiveData<List<RankAlbum>>()


    fun init(rankTag: RankTag) {
        this.rankTag = rankTag
        icon.value = rankTag.imgurl
        title.value = rankTag.rankname
        updateTime.value = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(Date()) + " 更新"
        isVol.value = rankTag.isvol
    }


    fun album(type: String) {
        rankServer.rankAlbum(type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.status == 1) {
                        when (type) {
                            "oneDay" -> {
                                songs.value = it.list!!
                            }
                            "sevenDays" -> {
                                monthSongs.value = it.list!!
                            }
                            else -> {
                                allSongs.value = it.list!!
                            }
                        }
                    }
                }, {
                    it.printStackTrace()
                })
    }
}