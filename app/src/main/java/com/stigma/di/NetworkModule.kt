package com.stigma.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.stigma.BuildConfig
import com.stigma.data.remote.AzureOpenAIApi
import com.stigma.data.remote.model.AzureOpenAIModel
import com.stigma.data.remote.provider.AzureOpenAIProvider
import com.stigma.data.remote.provider.DefaultAzureOpenAIProvider
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
object NetworkModule {
    
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }
    
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.AZURE_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    
    @Provides
    @Singleton
    fun provideAzureOpenAIApi(retrofit: Retrofit): AzureOpenAIApi {
        return retrofit.create(AzureOpenAIApi::class.java)
    }
    
    @Provides
    @Singleton
    fun provideAzureOpenAIProvider(
        azureOpenAIApi: AzureOpenAIApi
    ): AzureOpenAIProvider {
        return DefaultAzureOpenAIProvider(
            azureOpenAIApi = azureOpenAIApi,
            endpoint = BuildConfig.AZURE_OPENAI_ENDPOINT,
            version = BuildConfig.AZURE_OPENAI_API_VERSION
        )
    }
    
    @Provides
    @Singleton
    fun provideAzureOpenAIModel(
        provider: AzureOpenAIProvider
    ): AzureOpenAIModel {
        return AzureOpenAIModel(
            provider = provider,
            apiKey = BuildConfig.AZURE_OPENAI_KEY,
            deploymentId = BuildConfig.AZURE_OPENAI_DEPLOYMENT
        )
    }
}
