package com.kugou.repository.netmusic.rank

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize


class Songinfo {
    var songname: String? = null
}

@Parcelize
class RankTag : Parcelable {
    var rankid: Int = 0
    var ranktype: Int = 0
    var jump_url: String? = null
    var id: Int = 0
    @IgnoredOnParcel
    var children: List<String>? = null
    var banner7url: String? = null
    var intro: String? = null
    var update_frequency: String? = null
    var custom_type: Int = 0
    var imgurl: String? = null
    var haschildren: Int = 0
    var songinfo: List<Songinfo>? = null
    var bannerurl: String? = null
    var jump_title: String? = null
    var rankname: String? = null
    var isvol: Int = 0
}