package com.vision.wallpapers.api.unsplash

import com.vision.wallpapers.api.PexelsApi
import com.vision.wallpapers.api.unsplash.api.ACCESS_KEY
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UnsplashRetrofit {

    companion object{
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()

            logging.level = HttpLoggingInterceptor.Level.BODY

            val interceptor = object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val req = chain.request().newBuilder()
                            .addHeader("Authorization","Client-ID $ACCESS_KEY")
                            .build()
                    return chain.proceed(req)
                }
            }

            val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor (interceptor)
                    .build()

            Retrofit.Builder()
                    .baseUrl("https://api.unsplash.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
        }

        val api by lazy {
            retrofit.create(UnsplashApi::class.java)
        }
    }
}