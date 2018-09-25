package com.kugou.view.ui.fragment.netmusic.singer.detail

import androidx.lifecycle.MutableLiveData
import com.kugou.commom.BaseViewModel
import com.kugou.repository.netmusic.singer.SingerServer
import io.reactivex.android.schedulers.AndroidSchedulers

class DetailViewModel : BaseViewModel() {
    val server = SingerServer()

    val intro = MutableLiveData<String>()

    fun detail(singerId: Int) {
        server.singerDetail(singerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.status == 1) {
                        intro.value=it.data?.intro
                    }
                }, {
                    it.printStackTrace()
                })
    }
}