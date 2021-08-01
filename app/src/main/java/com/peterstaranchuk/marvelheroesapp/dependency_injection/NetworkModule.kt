package com.peterstaranchuk.marvelheroesapp.dependency_injection

import android.util.Log
import androidx.compose.ui.text.toLowerCase
import com.google.gson.Gson
import com.peterstaranchuk.common.HttpKeys
import com.peterstaranchuk.common.NetworkConstants
import com.peterstaranchuk.marvelheroesapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

val networkModule: Module = module {

    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .connectTimeout(NetworkConstants.CONNECT_TIMEOUT_IN_SEC, TimeUnit.SECONDS)
            .writeTimeout(NetworkConstants.WRITE_TIMEOUT_IN_SEC, TimeUnit.SECONDS)
            .readTimeout(NetworkConstants.READ_TIMEOUT_IN_SEC, TimeUnit.SECONDS)
            .addNetworkInterceptor(get(named("logs")))
            .addNetworkInterceptor(get(named("content_type")))
            .addNetworkInterceptor(get(named("auth")))
            .build()
    }

    single<Interceptor>(named("logs")) {
        HttpLoggingInterceptor {
            Log.d("Network", it)
        }.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    single<Interceptor>(named("content_type")) {
        Interceptor { chain ->
            val builder = chain.request().newBuilder().apply {
                header(HttpKeys.CONTENT_TYPE, "application/json")
            }
            chain.proceed(builder.build())
        }
    }

    single<Interceptor>(named("auth")) {
        Interceptor { chain ->
            val url = chain.request().url().newBuilder().apply {
                addQueryParameter(HttpKeys.API_KEY, BuildConfig.API_KEY)
                addQueryParameter(HttpKeys.TIMESTAMP, BuildConfig.TIMESTAMP)
                addQueryParameter(HttpKeys.HASH, BuildConfig.HASH.lowercase())
            }.build()

            chain.proceed(chain.request().newBuilder().url(url).build())
        }
    }

    single { Gson() }

}
