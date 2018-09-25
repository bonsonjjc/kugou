package com.kugou.view.ui.fragment.mv

import androidx.lifecycle.MutableLiveData
import com.kugou.commom.BaseViewModel
import com.kugou.repository.mv.MvServer
import com.kugou.repository.mv.Video
import io.reactivex.android.schedulers.AndroidSchedulers

class MvListViewModel : BaseViewModel() {
    val server: MvServer = MvServer()

    val dataList = MutableLiveData<List<Video>>()

    fun list(singerId: Int? = null, singerName: String? = null) {
        server.server(singerId, singerName, 1, 50)
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