package com.kugou.repository.mv

import com.kugou.server.convert.KugouConvert
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class MvServer {
    var mvRequest: MvRequest
    val baseUrl = "http://mobilecdngz.kugou.com/api/"

    init {
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor())
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(KugouConvert.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        mvRequest = retrofit.create(MvRequest::class.java)
    }

    fun server(singerid: Int?, singername: String?, page: Int, pageSize: Int): Observable<VideoList> {
        return mvRequest.mv(singerid, singername, page, pageSize)
                .subscribeOn(Schedulers.io())
    }
}

interface MvRequest {
    @GET("v3/singer/mv?with_res_tag=1")
    fun mv(@Query("singerid") singerid: Int?, @Query("singername") singername: String?, @Query("page") page: Int, @Query("pagesize") pageSize: Int): Observable<VideoList>
}