package com.example.rawg.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rawg.data.roomModel.RoomGameDetail
import com.example.rawg.data.roomModel.RoomPlatform
import com.example.rawg.data.roomModel.RoomPlatformItem
import com.example.rawg.data.roomModel.RoomPlatformRequirement
import com.example.rawg.domain.dao.FavoriteGameDao


@Database(entities = [RoomGameDetail::class, RoomPlatform::class, RoomPlatformItem::class, RoomPlatformRequirement::class], version = 1,exportSchema = false)
abstract class FavoriteGameDatabase : RoomDatabase() {
    abstract fun getFavoriteGameDao(): FavoriteGameDao
}