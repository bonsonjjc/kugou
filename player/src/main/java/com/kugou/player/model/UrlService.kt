package com.kugou.player.model

import com.google.gson.reflect.TypeToken
import com.kugou.audio.model.KGFile
import com.kugou.audio.model.Music
import com.kugou.server.http.Data
import com.kugou.server.http.OkHttp
import com.kugou.server.http.Result
import com.kugou.server.http.ResultMap
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class UrlService {
    private val request: OkHttp<OkHttp.Request> = OkHttp.Builder()
            .setBaseUrl("http://trackercdn.kugou.com")
            .builder(OkHttp.Request::class.java)

    fun songs(url: String): Observable<Result<Data<List<Music>>>> {
        return request.get(url)
                .map(ResultMap(object : TypeToken<Result<Data<List<Music>>>>() {}))
                .subscribeOn(Schedulers.io())
    }

    fun source(music: Music): Observable<KGFile> {
        val userId = "0"
        val mid = "248882481314299591952125499167454859886"
        val appid = "1005"
        val format = "%skgcloudv2%s%s%s"
        val key = Md5.md5(String.format(format, music.hash, appid, mid, userId)).toLowerCase()
        val v2 = "http://trackercdn.kugou.com/i/v2/?cmd=26&key=%s&hash=%s&pid=2&behavior=play&mid=%s&appid=%s&userid=%s&version=3001&vipType=1&token=&with_res_tag=1"
        val url = String.format(v2, key, music.hash, mid, appid, userId)

        return request.get(url)
                .subscribeOn(Schedulers.io())
                .map(ResultMap(ResultMap.baseType<KGFile>()))
                .map {
                    it.hash = music.hash
                    it
                }
    }
}