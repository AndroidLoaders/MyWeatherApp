package com.example.zooplsdemo.ui.networkadapter.retrofit

import com.example.zooplsdemo.BuildConfig
import com.example.zooplsdemo.ui.networkadapter.weatherdataprovider.WeatherDataServiceProvider
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val REQUEST_TIMEOUT = 15L

    @Provides
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    fun provideHttpInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()

    @Provides
    @Singleton
    fun provideOkHttp(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val httpBuilder = OkHttpClient().newBuilder()
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            httpBuilder.addInterceptor(interceptor.apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }).addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder().build()
                chain.proceed(request)
            }
        }
        return httpBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherDataService(retrofit: Retrofit): WeatherDataServiceProvider =
        retrofit.create(WeatherDataServiceProvider::class.java)
}