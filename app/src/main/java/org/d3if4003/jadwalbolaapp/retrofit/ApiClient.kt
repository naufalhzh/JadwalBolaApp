package org.d3if4003.jadwalbolaapp.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.d3if4003.jadwalbolaapp.util.Const
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private fun getInterceptor() : OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY;
        
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor{ it.proceed(it.request().newBuilder().addHeader("X-RapidApi-Key", Const.API_KEY).build())}
            .addInterceptor{ it.proceed(it.request().newBuilder().addHeader("X-RapidApi-Host", Const.API_HOST).build())}
            .build()
        
        return client
    }
    
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    fun getService() = getRetrofit().create(ApiService::class.java)
}