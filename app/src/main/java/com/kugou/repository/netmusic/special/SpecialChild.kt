package com.kugou.repository.netmusic.special

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class SpecialGroup(
        var id: Int = 0,
        var name: String?,
        var icon: String?,
        var has_child: Int?,
        var children: List<SpecialChild>?
)

@Parcelize
data class SpecialChild(
        var id: Int,
        var name: String?,
        var song_tag_id: Int,
        var special_tag_id: Int,
        var album_tag_id: Int,
        var imgurl: String?,
        var bannerurl: String?,
        var icon: String?,
        var jump_url: String?,
        var is_new: Int,
        var is_hot: Int,
        var has_child: Int
) : Parcelable

@Parcelize
class Special : Parcelable {
    var imgurl: String? = null
    var slid: Int = 0
    var singername: String? = null
    var collectcount: Int = 0
    var playcount: Int = 0
    var publishtime: String? = null
    var intro: String? = null
    var specialid: Int = 0
    var verified: Int = 0
    var suid: Int = 0
    var specialname: String? = null
    var username: String? = null
}
