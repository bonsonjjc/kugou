package com.kugou.view.ui.fragment.netmusic.album

import androidx.lifecycle.MutableLiveData
import com.kugou.commom.BaseViewModel
import com.kugou.repository.netmusic.album.AlbumServer
import com.kugou.repository.netmusic.album.Detail
import io.reactivex.android.schedulers.AndroidSchedulers

class AlbumDetailViewModel : BaseViewModel() {
    val service = AlbumServer()
    val dataList = MutableLiveData<Detail>()
    fun request(albumId: Int) {

        service.detail(albumId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.status == 1) {
                        dataList.value = it.data
                    }
                }, {
                    it.printStackTrace()
                })
    }
}