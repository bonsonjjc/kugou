package com.kugou.server.convert

import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException

class StringResponseBodyConverter : Converter<ResponseBody, String> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): String {
        var json = value.string()
        try {
            json = json.replace("<!--KG_TAG_RES_START-->", "")
            json = json.replace("<!--KG_TAG_RES_END-->", "")
            System.err.println("json$json")
            return json
        } finally {
            value.close()
        }
    }
}
