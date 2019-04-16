package com.edu.hcmut.movie.api

import com.edu.hcmut.movie.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Api {

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val LANGUAGE = "vi"

        private fun builder(): Retrofit {
            return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client())
                .build()
        }

        private fun client(): OkHttpClient {
            return OkHttpClient.Builder().addNetworkInterceptor { chain ->
                var request = chain.request()
                val url = request.url().newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY)
//                    .addQueryParameter("language", LANGUAGE)
                    .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }.build()
        }

        fun createService(): TheMovieDatabaseApi {
            return builder().create(TheMovieDatabaseApi::class.java)
        }
    }
}