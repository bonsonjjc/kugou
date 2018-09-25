package com.kugou.audio.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Music(var audio_id: Int,
                 var hash: String,
                 var m4afilesize: Int,
                 var filesize: Int,
                 var bitrate: Int,
                 var topic_url: String,
                 var feetype: Int,
                 var filename: String,
                 var addtime: String,
                 var album_audio_id: Int,
                 var mvhash: String,
                 var topic_url_sq: String,
                 var buy_count: Int,
                 var album_id: String,
                 var remark: String,
                 var extname: String,
                 var duration: Float,
                 var url: String? = null,
                 var path: String? = null
) : Parcelable

