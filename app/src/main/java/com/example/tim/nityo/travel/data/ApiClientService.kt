package com.example.tim.nityo.travel.data

import com.example.tim.nityo.travel.data.model.AttractionsData
import com.example.tim.nityo.travel.data.model.NityoResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiClientService {

    /**
     * 景點
     */
    @GET("{lang}/Attractions/All")
    fun getAttractions(
        @Path("lang") lang: String,
        @Query("page") page: Int
    ): Call<NityoResponse<AttractionsData>>

    companion object {
        fun create(): ApiClientService {
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .build()
                    chain.proceed(request)
                }.build()
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASEURL)
                .client(okHttpClient)
                .build()
            return retrofit.create(ApiClientService::class.java)
        }
        private const val BASEURL = "https://www.travel.taipei/open-api/"

    }

}