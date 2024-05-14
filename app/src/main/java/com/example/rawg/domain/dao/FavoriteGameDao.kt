package com.example.rawg.domain.dao

import androidx.room.*
import com.example.rawg.data.roomModel.RoomGameDetail
import com.example.rawg.data.roomModel.RoomPlatform
import com.example.rawg.data.roomModel.RoomPlatformItem
import com.example.rawg.data.roomModel.RoomPlatformRequirement
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteGameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameDetail(gameList: RoomGameDetail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlatform(platformList: List<RoomPlatform>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlatformItem(platformItemList: List<RoomPlatformItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlatformRequirement(platformRequirementList: List<RoomPlatformRequirement>)


    @Query("SELECT * FROM favoriteGame ORDER BY id ASC")
    fun getAllFavoriteGame(): Flow<List<RoomGameDetail>>

    @Query("SELECT * FROM favoriteGame where id = :id")
    fun getFavoriteGameById(id: Int): RoomGameDetail?

    @Query("SELECT * FROM platform WHERE gameId = :gameId ORDER BY id ASC")
    fun getPletform(gameId: Int): Flow<List<RoomPlatform>>

    @Query("SELECT * FROM platformItem WHERE platformId = :platformId ORDER BY id ASC")
    fun getPletformItem(platformId: Int): Flow<List<RoomPlatformItem>>

    @Query("SELECT * FROM platformRequirement WHERE platformId = :platformId ORDER BY id ASC")
    fun getPletformRequirement(platformId: Int): Flow<List<RoomPlatformRequirement>>

    @Query("DELETE FROM favoriteGame WHERE id = :id")
    suspend fun deleteGameDetail(id: Int)

}
