package com.kugou.view.ui.fragment.netmusic.album

import androidx.lifecycle.MutableLiveData
import com.kugou.commom.BaseViewModel
import com.kugou.repository.netmusic.album.Album
import com.kugou.repository.netmusic.album.AlbumServer
import io.reactivex.android.schedulers.AndroidSchedulers

class AlbumsViewModel : BaseViewModel() {
    val service = AlbumServer()

    val dataList = MutableLiveData<List<Album>>()

    fun onList(singerId: Int) {

        service.singer(singerId, 1, 50)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.status == 1) {
                        dataList.value = it.data!!.info
                    }
                }, {
                    it.printStackTrace()
                })
    }
}