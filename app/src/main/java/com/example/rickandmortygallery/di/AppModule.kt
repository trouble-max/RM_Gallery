package com.example.rickandmortygallery.di

import com.example.rickandmortygallery.data.remote.RickAndMortyApi
import com.example.rickandmortygallery.repository.RickAndMortyRepository
import com.example.rickandmortygallery.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRickAndMortyRepository (
        api: RickAndMortyApi
    ) = RickAndMortyRepository(api)

    @Singleton
    @Provides
    fun provideRickAndMortyApi(): RickAndMortyApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RickAndMortyApi::class.java)
    }
}