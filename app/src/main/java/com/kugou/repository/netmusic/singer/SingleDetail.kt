package com.kugou.repository.netmusic.singer


data class SingleDetail(
        val mvcount: Int,
        val has_long_intro: Int,
        val intro: String,
        val songcount: Int,
        val imgurl: String,
        val profile: String,
        val singerid: Int,
        val singername: String,
        val albumcount: Int
)
