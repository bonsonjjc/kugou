package com.kugou.repository.netmusic.singer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Singer(
        val heatoffset: Int,
        val sortoffset: Int,
        val singername: String,
        val intro: String,
        val songcount: Int,
        val imgurl: String,
        val albumcount: Int,
        val mvcount: Int,
        val singerid: Int,
        val heat: Int,
        val fanscount: Int,
        val is_settled: Int
) : Parcelable

class SingerGroup {
    var title: String? = null
    var singer: List<Singer>? = null
}