package cl.mobdev.androidtest.session.network

import cl.mobdev.androidtest.session.network.config.WebServiceConfig.Timeout
import cl.mobdev.androidtest.session.network.config.WebServiceConfig.Url.URL_PROD
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Networking {
    fun getRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(Timeout.CONNECT, TimeUnit.SECONDS)
            .readTimeout(Timeout.CONNECT, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(URL_PROD)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}