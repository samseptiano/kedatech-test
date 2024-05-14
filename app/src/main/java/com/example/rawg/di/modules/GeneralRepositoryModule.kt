package com.example.rawg.di.modules

import com.example.rawg.data.repository.SampleDataSourceImpl
import com.example.rawg.data.repository.datasource.SampleDataSource
import com.example.rawg.domain.dao.FavoriteGameDao
import com.example.rawg.domain.service.SampleServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GeneralRepositoryModule {
    @Singleton
    @Provides
    fun provideSampleDataSource(apiService: SampleServices, favoritDao: FavoriteGameDao): SampleDataSource {
        return SampleDataSourceImpl(apiService, favoritDao)
    }

}