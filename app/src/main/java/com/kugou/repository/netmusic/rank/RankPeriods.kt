package com.kugou.repository.netmusic.rank

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class RankYear : Volid {
    val year: Int = 0
    val vols: List<RankWeek>? = null
    override fun name(): String {
        return "$year"
    }

    override fun value(): Int {
        return -1
    }
}

@Parcelize
class RankWeek : Volid {

    var volid: Int = 0
    var volname: String? = null


    override fun name(): String {
        return "$volname"
    }

    override fun value(): Int {
        return volid
    }
}


interface Volid : Parcelable {
    fun name(): String
    fun value(): Int
}