package com.kevincpchang.example.currencyconverter.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { CurrencyConverterClient() }
    factory { provideOkHttpClient(get()) }
    factory { provideRestApi(get()) }
    single { provideRetrofit(get()) }
}

/* Fetching currency data from currencylayer api
   by Retrofit and OkHttp Client.
 */
fun getBaseUrl(): String {
    return "http://api.currencylayer.com/"
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(getBaseUrl()).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(authInterceptor: CurrencyConverterClient): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(authInterceptor).build()
}

fun provideRestApi(retrofit: Retrofit): CurrencyConverterApi = retrofit.create(CurrencyConverterApi::class.java)

class CurrencyConverterClient : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .build()
        return chain.proceed(newRequest)
    }
}