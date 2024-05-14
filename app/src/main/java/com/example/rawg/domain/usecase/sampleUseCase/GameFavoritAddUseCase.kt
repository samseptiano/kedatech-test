package com.example.rawg.domain.usecase.sampleUseCase

import com.example.rawg.data.repository.datasource.SampleDataSource
import com.example.rawg.base.domain.usecase.BaseUseCase
import com.example.rawg.data.modelMapper.GameItem
import com.example.rawg.data.roomModel.RoomGameDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GameFavoritAddUseCase @Inject constructor(private val repository: SampleDataSource) :
    BaseUseCase<Unit, GameFavoritAddUseCase.Params>() {

    class Params(val gameItem: GameItem)

    override suspend fun run(params: Params) {
       return repository.addGameToFavorit(params.gameItem)
    }

}