package com.example.rawg.data.repository

import com.example.rawg.base.apihandler.ApiHandler
import com.example.rawg.base.data.ResponseWrapper
import com.example.rawg.base.data.ResultState
import com.example.rawg.data.modelMapper.GameDetail
import com.example.rawg.data.modelMapper.GameItem
import com.example.rawg.data.repository.datasource.SampleDataSource
import com.example.rawg.data.roomModel.RoomGameDetail
import com.example.rawg.domain.dao.FavoriteGameDao
import com.example.rawg.domain.service.SampleServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SampleDataSourceImpl @Inject constructor(
    private val sampleServices: SampleServices,
    private val favoriteGameDao: FavoriteGameDao
) : SampleDataSource {

    override suspend fun getGameList(
        page: Int?,
        pageCount: Int?,
        search: String?
    ): ResultState<ResponseWrapper<List<GameItem?>?>> {
        return try {
            val result = ApiHandler.handleApi {
                sampleServices.getGameList(page = page, pageSize = pageCount, search = search)
            }


            ResultState.Success(ResponseWrapper(
                result?.count ?: 0,
                result?.next ?: "",
                result?.previous ?: "",
                result?.results?.map { it?.toGameItem() }
            ))
        } catch (e: Exception) {
            ResultState.Error(e)
        }

    }


    override suspend fun getGameDetail(id: Int): ResultState<GameDetail?> {
        try {
            ApiHandler.handleApi {
                sampleServices.getGameDetail(gameId = id)
            }.apply {
                return ResultState.Success(this?.toGameDetail())
            }
        } catch (e: Exception) {
            return ResultState.Error(e)
        }

    }

    override suspend fun getAllGameFavorit(): Flow<List<RoomGameDetail>> = flow {
         favoriteGameDao.getAllFavoriteGame().collect {
                emit(it)
        }
    }.catch {  }

    override suspend fun getGameToFavoritById(id: Int): RoomGameDetail? {
        return favoriteGameDao.getFavoriteGameById(id)
    }

    override suspend fun addGameToFavorit(gameItem: GameItem) {
        favoriteGameDao.insertGameDetail(RoomGameDetail.toRoomGameDetail(gameItem))
    }

    override suspend fun removeToFavorit(gameItem: GameItem) {
        favoriteGameDao.deleteGameDetail(gameItem.id)
    }

}