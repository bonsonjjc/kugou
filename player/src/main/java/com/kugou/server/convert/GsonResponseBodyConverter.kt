package com.kugou.server.convert

import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException

internal class GsonResponseBodyConverter<T>(private val adapter: TypeAdapter<T>) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        var json = value.string()
        try {
            json = json.replace("<!--KG_TAG_RES_START-->", "")
            json = json.replace("<!--KG_TAG_RES_END-->", "")
            System.err.println("json$json")
            return adapter.fromJson(json)
        } finally {
            value.close()
        }
    }
}
