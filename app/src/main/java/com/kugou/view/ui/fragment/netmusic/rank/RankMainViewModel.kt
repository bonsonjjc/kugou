package com.kugou.view.ui.fragment.netmusic.rank

import androidx.lifecycle.MutableLiveData
import android.util.Log
import com.kugou.commom.BaseViewModel
import com.kugou.repository.netmusic.rank.RankServer
import com.kugou.repository.netmusic.rank.RankTag
import io.reactivex.android.schedulers.AndroidSchedulers

class RankMainViewModel : BaseViewModel() {
    val rankServer = RankServer()
    val ranktags = MutableLiveData<List<RankTag>>()

    fun request() {
        rankServer.rankTags()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.status == 1) {
                        ranktags.value = it.data!!.info
                    } else {
                        Log.e("tag", it.error + "")
                    }
                }, {
                    it.printStackTrace()
                })
    }
}