package com.example.rawg.domain.usecase.sampleUseCase

import com.example.rawg.data.repository.datasource.SampleDataSource
import com.example.rawg.base.domain.usecase.BaseUseCase
import com.example.rawg.data.roomModel.RoomGameDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GameFavoritUseCase @Inject constructor(private val repository: SampleDataSource) :
    BaseUseCase<Flow<List<RoomGameDetail>>, GameFavoritUseCase.Params>() {

    class Params

    override suspend fun run(params: Params): Flow<List<RoomGameDetail>> {
       return repository.getAllGameFavorit()
    }

}