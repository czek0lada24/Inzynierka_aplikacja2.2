package com.example.barcodescanner

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retro {

        fun getRetroInstance(BASE:String):Retrofit{
            val gson = GsonBuilder().setLenient().create()

            return Retrofit.Builder()
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()


    }
}