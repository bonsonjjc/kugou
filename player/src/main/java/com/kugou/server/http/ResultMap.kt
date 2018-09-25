package com.kugou.server.http

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken

import io.reactivex.functions.Function

class ResultMap<T>(dataType: TypeToken<T>) : Function<String, T> {
    private val adapter: TypeAdapter<T>


    @Throws(Exception::class)
    override fun apply(dataResult: String): T {
        return adapter.fromJson(dataResult)
    }

    companion object {
        protected var gson = Gson()

        inline fun <reified T> baseType(): TypeToken<T> {
            return object : TypeToken<T>() {}
        }
//
//        inline fun <reified T> result(): TypeToken<Result<T>> {
//            return object : TypeToken<Result<T>>() {}
//        }
//
//        inline fun <reified T> data(): TypeToken<Result<Data<T>>> {
//            return object : TypeToken<Result<Data<T>>>() {}
//        }
    }
    init {
        adapter = gson.getAdapter(dataType)
    }
}
