package com.kugou.audio.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class KGFile : Parcelable {
    var hash: String? = ""
    var status: Int = 0
    var fileHead: Int = 0
    var fileSize: Int = 0
    var fileName: String? = ""
    var extName: String? = ""
    var bitRate: Int = 0
    var timeLength: Int = 0
    var url: Array<String>? = null
}
