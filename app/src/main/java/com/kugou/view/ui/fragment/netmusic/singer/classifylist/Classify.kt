package com.kugou.view.ui.fragment.netmusic.singer.classifylist

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Classify(var name: String, var type: Int, var sexType: Int, var musician: Int = 0) : Parcelable
