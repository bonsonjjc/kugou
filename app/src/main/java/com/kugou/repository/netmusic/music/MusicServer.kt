package com.kugou.repository.netmusic.music

import com.kugou.audio.model.Music
import com.kugou.server.http.Data
import com.kugou.server.http.OkHttp
import com.kugou.server.http.Result
import com.kugou.server.http.ResultMap
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class MusicServer {
    var request: OkHttp<OkHttp.Request> = OkHttp.Builder()
            .setBaseUrl("http://mobilecdngz.kugou.com/api/")
            .builder(OkHttp.Request::class.java)

    fun singer(singerId: Int, sortType: Int, page: Int, pageSize: Int): Observable<Result<Data<List<Music>>>> {
        return request.get("v3/singer/song?sorttype=$sortType&version=8983&plat=0&pagesize=$pageSize&singerid=$singerId&area_code=1&page=$page&with_res_tag=1")
                .subscribeOn(Schedulers.io())
                .map(ResultMap(ResultMap.baseType()))
    }

    fun songList(url: String, page: Int, pageSize: Int): Observable<Result<Data<List<Music>>>> {
        return request.get(url + "&page=$page&pagesize=$pageSize&with_res_tag=1")
                .subscribeOn(Schedulers.io())
                .map(ResultMap(ResultMap.baseType()))
    }
}
/*

interface MusicRequest {
    @GET("v5/singer/song?plat=0&area_code=1&version=8940&with_res_tag=1")
    fun singer(@Query("singerid") singerId: Int, @Query("sorttype") sorttype: Int, @Query("page") page: Int, @Query("pagesize") pagesize: Int): Observable<MusicList>

    @GET("v5/special/song?plat=0&area_code=1&version=8940&with_res_tag=1")
    fun special(@Query("specialid") specialId: Int, @Query("page") page: Int, @Query("pagesize") pagesize: Int): Observable<MusicList>

    @GET("v5/rank/song?area_code=1&version=8948&ranktype=2&plat=0&with_res_tag=1")
    fun rank(@Query("rankid") rankId: Int, @Query("volid") volid: Int, @Query("page") page: Int, @Query("pagesize") pagesize: Int): Observable<MusicList>

    @GET("v5/rank/newsong?with_cover=1&plat=0&area_code=1&version=8940&with_res_tag=1")
    fun newSongs(@Query("type") type: Int, @Query("page") page: Int, @Query("pagesize") pageSize: Int)

    @GET("v5/search/song?tag=1&area_code=1&highlight=em&plat=0&sver=5&api_ver=1&showtype=14&tag_aggr=1&version=8940&correct=1&with_res_tag=1")
    fun search(@Query("keyword", encoded = true) keyword: String, @Query("tagtype") tagtype: String, @Query("page") page: Int, @Query("pagesize") pageSize: Int)
}*/
