package com.kugou.repository.netmusic.special

import com.google.gson.reflect.TypeToken
import com.kugou.server.http.Data
import com.kugou.server.http.OkHttp
import com.kugou.server.http.Result
import com.kugou.server.http.ResultMap
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class SpecialService {
    //分类    http://mobilecdnbj.kugou.com/api/v3/tag/list?apiver=2&plat=0&pid=0
//推荐    http://mobilecdnbj.kugou.com/api/v3/tag/recommend?apiver=2&plat=0&showtype=3
    var request: OkHttp<OkHttp.Request> = OkHttp.Builder()
            .setBaseUrl("http://mobilecdnbj.kugou.com/api/")
            .builder(OkHttp.Request::class.java)

    fun tags(): Observable<Result<Data<List<SpecialGroup>>>> {
        return request.get("v3/tag/list?apiver=2&plat=0&pid=0").subscribeOn(Schedulers.io())
                .map(ResultMap(object : TypeToken<Result<Data<List<SpecialGroup>>>>() {}))
    }

    fun type(id: Int, tagId: Int, page: Int, pageSize: Int): Observable<Result<Data<List<Special>>>> {
        return request.get("http://mobilecdngz.kugou.com/api/v3/tag/specialList?plat=2&id=$id&tagid=$tagId&page=$page&pagesize=$pageSize&sort=2&ugc=1")
                .subscribeOn(Schedulers.io())
                .map(ResultMap(object : TypeToken<Result<Data<List<Special>>>>() {}))
    }
}