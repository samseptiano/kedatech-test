package com.example.rawg.di.modules

import com.example.rawg.base.coroutine.AppDispatchers
import com.example.rawg.base.coroutine.AppDispatchersImpl
import com.example.rawg.di.qualifier.GameOkHttpClientQualifier
import com.example.rawg.domain.service.SampleServices
import com.example.rawg.utils.CONSTANTS.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
class NetworkApiModule {

    @Singleton
    @Provides
    fun buildSampleServicesClient(
        @GameOkHttpClientQualifier
        okHttpClient: OkHttpClient,
        coroutineCallAdapterFactory: CoroutineCallAdapterFactory,
        gsonConverterFactory: GsonConverterFactory
    ): SampleServices {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(coroutineCallAdapterFactory)
            .build()
            .create(SampleServices::class.java)
    }

    @Singleton
    @Provides
    @GameOkHttpClientQualifier
    fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().also { item ->
            val log = HttpLoggingInterceptor()
            log.level = HttpLoggingInterceptor.Level.BODY
            item.addInterceptor(log)
            item.retryOnConnectionFailure(true)
        }.build()
    }

    @Provides
    @Singleton
    fun provideAppDispatchers(): AppDispatchers {
        return AppDispatchersImpl()
    }

    @Provides
    @Singleton
    fun getGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun getCoroutineCallAdapter(): CoroutineCallAdapterFactory {
        return CoroutineCallAdapterFactory.invoke()
    }

}