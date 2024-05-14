package com.example.rawg.domain.usecase.sampleUseCase

import com.example.rawg.data.repository.datasource.SampleDataSource
import com.example.rawg.base.domain.usecase.BaseUseCase
import com.example.rawg.data.modelMapper.GameItem
import com.example.rawg.data.roomModel.RoomGameDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GameFavoritItemUseCase @Inject constructor(private val repository: SampleDataSource) :
    BaseUseCase<Any, GameFavoritItemUseCase.Params>() {

    class Params(val id: Int)

    override suspend fun run(params: Params) : Any {

        return try{
            repository.getGameToFavoritById(params.id)
            true
        }catch (e:Exception){
            false
        }
    }

}