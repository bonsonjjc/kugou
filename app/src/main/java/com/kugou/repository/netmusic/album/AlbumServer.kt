package com.kugou.repository.netmusic.album

import com.kugou.server.http.Data
import com.kugou.server.http.OkHttp
import com.kugou.server.http.Result
import com.kugou.server.http.ResultMap
import com.kugou.audio.model.Music
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class AlbumServer {
    var request: OkHttp<OkHttp.Request> = OkHttp.Builder()
            .setBaseUrl("http://mobilecdngz.kugou.com/api/")
            .builder(OkHttp.Request::class.java)

    fun singer(singerId: Int, page: Int, pageSize: Int): Observable<Result<Data<List<Album>>>> {
        return request.get("v5/singer/album?plat=0&version=8940&with_res_tag=1&singerid=${singerId}&page=${page}&pagesize=${pageSize}")
                .subscribeOn(Schedulers.io())
                .map(ResultMap(ResultMap.baseType()))
    }

    fun songs(albumId: Int, page: Int, pageSize: Int): Observable<Result<Data<List<Music>>>> {
        return request.get("v3/album/song?version=8983&albumid=${albumId}&plat=0&page=${page}&pagesize=${pageSize}&area_code=1&with_res_tag=1")
                .subscribeOn(Schedulers.io())
                .map(ResultMap(ResultMap.baseType()))
    }


    fun detail(albumId: Int): Observable<Result<Detail>> {
        return request.get("v3/album/info?albumid=${albumId}&with_res_tag=1")
                .subscribeOn(Schedulers.io())
                .map(ResultMap(ResultMap.baseType()))
    }
}