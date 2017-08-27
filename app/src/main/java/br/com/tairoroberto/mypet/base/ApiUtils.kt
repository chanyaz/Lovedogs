package br.com.tairoroberto.mypet.base

import com.facebook.stetho.okhttp3.StethoInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


/**
 * Created by tairo on 8/24/17.
 */
class ApiUtils {
    private val URL_BASE_METRO = "http://192.168.15.5:3000"

    fun getApiService(): Api? {
        return RetrofitClient.getRetrofit(URL_BASE_METRO)?.create(Api::class.java)
    }
}

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getRetrofit(baseUrl: String): Retrofit? {
        if (retrofit == null) {

            val client = OkHttpClient.Builder()
                    .addNetworkInterceptor(StethoInterceptor())
                    .build()

            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
        }

        return retrofit
    }
}