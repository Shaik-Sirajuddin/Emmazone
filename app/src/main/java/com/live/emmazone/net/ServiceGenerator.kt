package com.live.emmazone.net

import android.util.Log
import com.google.gson.GsonBuilder
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.utils.App
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceGenerator {

    private val httpClient = OkHttpClient.Builder()
        .readTimeout((3 * 60).toLong(), TimeUnit.SECONDS)
        .connectTimeout((3 * 60).toLong(), TimeUnit.SECONDS)
        .writeTimeout((3 * 60).toLong(), TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(provideHeaderInterceptor())
        .connectionSpecs(listOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT)).build()
    private val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .setLenient()
        .create()

    private val builder = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())


    @JvmStatic
    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = builder.client(httpClient)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setPrettyPrinting().create()
                )
            )
            .build()
        return retrofit.create(serviceClass)
    }

    @JvmStatic
    fun getRetrofit(): Retrofit {
        return builder.client(httpClient)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setPrettyPrinting().create()
                )
            )
            .build()
    }


    private fun provideHeaderInterceptor(): Interceptor {

        return Interceptor { chain ->
            val request: Request
            if (getPreference(AppConstants.AUTHORIZATION, "").isNotEmpty()) {
                Log.d("Authorization", "Bearer " + getPreference(AppConstants.AUTHORIZATION, ""))
                request = chain.request().newBuilder()
                    .header("Authorization", "Bearer " + getPreference(AppConstants.AUTHORIZATION, ""))
                    .header("securitykey", AppConstants.SECURITY_KEY)
                    .header("Accept", "application/json")
                    .build()
            } else {
                request = chain.request().newBuilder()
                    .header("Accept", "application/json")
                    .header("securitykey", AppConstants.SECURITY_KEY)
                    .build()
            }

            chain.proceed(request)
        }
    }

}