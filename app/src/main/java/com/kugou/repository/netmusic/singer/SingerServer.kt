package com.kugou.repository.netmusic.singer

import com.kugou.server.http.Data
import com.kugou.server.http.OkHttp
import com.kugou.server.http.Result
import com.kugou.server.http.ResultMap
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class SingerServer {
    var request: OkHttp<OkHttp.Request> = OkHttp.Builder()
            .setBaseUrl("http://mobilecdngz.kugou.com/api/")
            .builder(OkHttp.Request::class.java)

    fun singerList(musician: Int, type: Int, sextype: Int): Observable<Result<Data<List<SingerGroup>>>> {
        return request.get("v5/singer/list?showtype=2&with_res_tag=1&musician=${musician}&type=${type}&sextype=${sextype}")
                .subscribeOn(Schedulers.io())
                .map(ResultMap(ResultMap.baseType()))
    }

    fun singerHeat(short: Int): Observable<Result<Data<List<Singer>>>> {
        return request.get("v5/singer/list?version=8940&showtype=1&plat=0&sextype=0&pagesize=100&type=0&page=1&musician=0&sort=${short}")
                .subscribeOn(Schedulers.io())
                .map(ResultMap(ResultMap.baseType()))
    }

    fun singerDetail(singerId: Int): Observable<Result<SingleDetail>> {
        return request.get("v5/singer/info?with_res_tag=1&singerid=${singerId}")
                .subscribeOn(Schedulers.io())
                .map(ResultMap(ResultMap.baseType()))
    }
}