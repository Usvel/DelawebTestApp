package com.example.delawebtestapp.di

import com.example.delawebtestapp.data.NewsApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object RetrofitModule {

    private const val URL = "https://newsapi.org/v2/"
    //825bcfa7c7544a229c61e1f197a8a478
    private const val API_KEY = "0bb6383f2e0a44b8b2706c571cb5921a"
    const val PAGE_SIZE = 10

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }

    @Provides
    @Singleton
    fun provideHttpApiKeyInterceptor(): Interceptor {
        val interceptor = Interceptor { chain ->
            val origin = chain.request()
            val httpUrl = origin.url
            val newHttpUrl =
                httpUrl.newBuilder().addQueryParameter("pageSize", PAGE_SIZE.toString())
                    .addQueryParameter("apiKey", API_KEY).build()
            val request = origin.newBuilder().url(newHttpUrl).build()
            chain.proceed(request)
        }
        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkHttp3Client(
        loggingInterceptor: HttpLoggingInterceptor,
        apiKeyInterceptor: Interceptor
    ) =
        OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRxJavaAdapter(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .baseUrl(URL)
            .build()

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }
}