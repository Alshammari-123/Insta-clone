package com.example.finle_project.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class API {

    companion object {
        private var retrofit: Retrofit? = null

        init {
            retrofit = Retrofit.Builder()
                .baseUrl("https://61b1a46ec8d4640017aaee15.mockapi.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getInstance(): Retrofit {
            return retrofit!!
        }


    }
}