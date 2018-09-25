package com.kugou.view.ui.fragment.netmusic.rank.single

import androidx.lifecycle.MutableLiveData
import com.kugou.commom.BaseViewModel
import com.kugou.audio.model.Music
import com.kugou.repository.netmusic.rank.*
import io.reactivex.android.schedulers.AndroidSchedulers
import java.text.SimpleDateFormat
import java.util.*

class SingleRanklViewModel : BaseViewModel() {
    private val rankServer = RankServer()

    lateinit var rankTag: RankTag

    val icon = MutableLiveData<String>()

    var title = MutableLiveData<String>()

    val updateTime = MutableLiveData<String>()

    val isVol = MutableLiveData<Int>()

    var songs = MutableLiveData<List<Music>>()
    var monthSongs = MutableLiveData<List<Music>>()
    var allSongs = MutableLiveData<List<Music>>()


    fun init(rankTag: RankTag) {
        this.rankTag = rankTag
        icon.value = rankTag.imgurl
        title.value = rankTag.rankname
        updateTime.value = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(Date()) + " 更新"
        isVol.value = rankTag.isvol
    }

    fun single(type: String) {
        rankServer.byCount(type, 1, 30)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.status == 1) {
                        when (type) {
                            "day" -> {
                                songs.value = it.data!!.info!!
                            }
                            "month" -> {
                                monthSongs.value = it.data!!.info!!
                            }
                            else -> {
                                allSongs.value = it.data!!.info!!
                            }
                        }
                    }
                }, {
                    it.printStackTrace()
                })
    }
}