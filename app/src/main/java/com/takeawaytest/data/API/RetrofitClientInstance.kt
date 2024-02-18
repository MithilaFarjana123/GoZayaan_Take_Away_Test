package com.takeawaytest.data.API

import com.takeawaytest.data.API.EndPoint.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClientInstance {


    lateinit var retrofit: Retrofit

    val retrofitInstance: Retrofit
        get() {
            if (!this::retrofit.isInitialized) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                val okHttpClient = OkHttpClient.Builder()
                    .readTimeout(180, TimeUnit.SECONDS)
                    .connectTimeout(180, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .build()


                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            }
            return retrofit
        }


}