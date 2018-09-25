package com.kugou.repository.netmusic.rank

import com.kugou.server.http.Data
import com.kugou.server.http.OkHttp
import com.kugou.server.http.Result
import com.kugou.server.http.ResultMap
import com.kugou.audio.model.Music
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class RankServer {
    var request: OkHttp<OkHttp.Request> = OkHttp.Builder()
            .setBaseUrl("http://mobilecdngz.kugou.com/api/")
            .builder(OkHttp.Request::class.java)

    fun rankTags(): Observable<Result<Data<List<RankTag>>>> {
        val map = mutableMapOf<String, Any>()
        map.put("apiver", 5)
        map.put("withsong", 1)
        map.put("showtype", 2)
        map.put("plat", 0)
        map.put("parentid", 0)
        map.put("area_code", 1)
        map.put("version", 8948)
        map.put("with_res_tag", 1)
        return request.get("v3/rank/list", map)
                .subscribeOn(Schedulers.io())
                .map(ResultMap(ResultMap.baseType()))
    }

    fun rankPeriods(rankType: Int, rankId: Int): Observable<Result<Data<List<RankYear>>>> {
        val map = mutableMapOf<String, Any>()
        map.put("plat", 0)
        map.put("with_res_tag", 0)
        map.put("ranktype", rankType)
        map.put("rankid", rankId)
        return request.get("v3/rank/vol", map)
                .subscribeOn(Schedulers.io())
                .map(ResultMap(ResultMap.baseType()))
    }

    fun rankSongs(rankId: Int, volid: Int?, page: Int, pagesize: Int): Observable<Result<Data<List<Music>>>> {
        val map = mutableMapOf<String, Any>()
        map.put("rankid", rankId)
        map.put("ranktype", 2)
        if (volid != null)
            map.put("volid", volid)
        map.put("page", page)
        map.put("pagesize", pagesize)
        return request.get("v3/rank/song?version=8983&plat=0&area_code=1&with_res_tag=1", map)
                .subscribeOn(Schedulers.io())
                .map(ResultMap(ResultMap.baseType()))
    }

    fun rankAlbum(type: String): Observable<RankAlbumList> {
        return request.get("http://zhuanjistatic.kugou.com/v3/albumRankingList/$type?appid=1005&version=8969&channel=56")
                .subscribeOn(Schedulers.io())
                .map(ResultMap(ResultMap.baseType()))
    }

    fun byCount(type: String, page: Int, pagesize: Int): Observable<Result<Data<List<Music>>>> {
        val map = mutableMapOf<String, Any>()
        map.put("type", type)
        map.put("page", page)
        map.put("pagesize", pagesize)
        return request.get("v5/rank/buycount?area_code=1&appid=1005&with_res_tag=1", map)
                .subscribeOn(Schedulers.io())
                .map(ResultMap(ResultMap.baseType()))
    }

}