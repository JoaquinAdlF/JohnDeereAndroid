package com.example.johndeere.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
Construcción del cliente para el API con la url base.
 */

object ServiceBuilder {

    private val client = OkHttpClient.Builder().build()
    private const val BASE_URL = "https://lsmsystem-api.vercel.app/"


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>) : T {
        return retrofit.create(service)
    }


}