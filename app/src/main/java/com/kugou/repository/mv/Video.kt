package com.kugou.repository.mv

import com.kugou.server.http.Data
import com.kugou.server.http.Result

class Video {
    var imgurl: String? = null
    var singername: String? = null
    var filename: String? = null
    var intro: String? = null
    var hash: String? = null
}

class VideoList : Result<Data<List<Video>>>()
