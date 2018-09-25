package com.kugou.repository.netmusic.album

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Album : Parcelable {
    var albumname: String? = null
    var imgurl: String? = null
    var songcount: Int = 0
    var singername: String? = null
    var singerid: Int = 0
    var buycount: Int = 0
    var publishtime: String? = null
    var intro: String? = null
    var albumid: Int = 0
    var privilege: Int = 0
    var isfirst: Int = 0
}

data class Detail(
        var albumname: String?,
        var publishtime: String?,
        var singername: String?,
        var intro: String?,
        var songcount: Int,
        var imgurl: String?,
        var collectcount: Int,
        var singerid: Int,
        var albumid: Int,
        var sextype: Int,
        var privilege: Int)
