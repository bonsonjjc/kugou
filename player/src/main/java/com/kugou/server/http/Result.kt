package com.kugou.server.http

open class Result<T> {
    var status: Int =0
    var error: String? = null
    var errorcode: Int = 0
    var data: T? = null
}

open class Data<T> {
    var timestamp: Long = 0
    var info: T? = null
    var total: Int = 0
}
