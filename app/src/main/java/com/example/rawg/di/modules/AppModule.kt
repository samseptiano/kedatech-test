package com.example.rawg.di.modules


import android.content.Context
import androidx.room.Room
import com.example.rawg.domain.dao.FavoriteGameDao
import com.example.rawg.domain.database.FavoriteGameDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun providesFavoriteGameDatabase(@ApplicationContext context: Context): FavoriteGameDatabase =
        Room.databaseBuilder(context, FavoriteGameDatabase::class.java, "favoriteGame")
            .build()

    @Provides
    fun providesFavoritGameDao(favoriteGameDatabase: FavoriteGameDatabase): FavoriteGameDao =
        favoriteGameDatabase.getFavoriteGameDao()


}