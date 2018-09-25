package com.kugou.repository.netmusic.rank


data class RankAlbum(
        var update_time: String?,
        var version: String?,
        var single_buy_num: Int,
        var company: String?,
        var total_money: Int,
        var singerid: String?,
        var kubi_price: String?,
        var img: String?,
        var hot: String?,
        var ranking_change: Int,
        var is_can_goto_buy_page: Int,
        var is_for_sale: Int,
        var albumid: String?,
        var is_can_single: Int,
        var singername: String?,
        var pay_type: String?,
        var buy_count: Int,
        var pkg_price: String?,
        var privilege: String?,
        var topic_url: String?,
        var is_new: Int,
        var b: Int,
        var albumname: String?,
        var publish_time: String?,
        var language: String?
)

class RankAlbumList {
    var cdn_update_time: String? = ""
    var status: Int = 0
    var err_code: Int = 0
    var err_msg: String? = ""
    var list: List<RankAlbum>? = null
}
