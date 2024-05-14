package com.example.rawg.data.repository.datasource

import com.example.rawg.base.data.ResponseWrapper
import com.example.rawg.base.data.ResultState
import com.example.rawg.data.modelMapper.GameDetail
import com.example.rawg.data.modelMapper.GameItem
import com.example.rawg.data.roomModel.RoomGameDetail
import kotlinx.coroutines.flow.Flow


interface SampleDataSource {
    suspend fun getGameList(page: Int?, pageCount: Int?, search: String?): ResultState<ResponseWrapper<List<GameItem?>?>>
    suspend fun getGameDetail(id: Int): ResultState<GameDetail?>
    suspend fun getAllGameFavorit(): Flow<List<RoomGameDetail>>
    suspend fun getGameToFavoritById(id: Int): RoomGameDetail?
    suspend fun addGameToFavorit(gameItem: GameItem)
    suspend fun removeToFavorit(gameItem: GameItem)
}