package com.kugou.view.ui.fragment.netmusic.singer.classify

import androidx.lifecycle.MutableLiveData
import com.kugou.commom.BaseViewModel
import com.kugou.repository.netmusic.singer.SingerGroup
import com.kugou.repository.netmusic.singer.SingerServer
import io.reactivex.android.schedulers.AndroidSchedulers

class ClassifyViewModel : BaseViewModel() {

    val singerServer: SingerServer = SingerServer()
    val hostList = MutableLiveData<List<SingerGroup>>()


    fun list(musician: Int, type: Int, sexType: Int) {
        singerServer.singerList(musician, type, sexType)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.status == 1) {
                        hostList.value = it.data!!.info!!
                    }
                }, {
                    it.printStackTrace()
                })
    }

}