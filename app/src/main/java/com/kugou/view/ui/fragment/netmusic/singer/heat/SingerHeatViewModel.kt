package com.kugou.view.ui.fragment.netmusic.singer.heat

import androidx.lifecycle.MutableLiveData
import com.kugou.commom.BaseViewModel
import com.kugou.repository.netmusic.singer.Singer
import com.kugou.repository.netmusic.singer.SingerServer
import io.reactivex.android.schedulers.AndroidSchedulers

class SingerHeatViewModel : BaseViewModel() {
    val singerServer: SingerServer = SingerServer()

    val hostList = MutableLiveData<List<Singer>>()
    val roaringList = MutableLiveData<List<Singer>>()

    fun hostList(short: Int) {
        singerServer.singerHeat(short)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.status == 1) {
                        if (short == 1) {
                            hostList.value = it.data!!.info!!
                        } else {
                            roaringList.value = it.data!!.info!!
                        }
                    }
                }, {
                    it.printStackTrace()
                })
    }
}