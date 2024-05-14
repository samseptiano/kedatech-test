package com.example.rawg.domain.usecase.sampleUseCase

import com.example.rawg.base.data.ResponseWrapper
import com.example.rawg.base.data.ResultState
import com.example.rawg.data.repository.datasource.SampleDataSource
import com.example.rawg.base.domain.usecase.BaseUseCase
import com.example.rawg.data.modelMapper.GameDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GameDetailUseCase @Inject constructor(private val repository: SampleDataSource) :
    BaseUseCase<ResultState<GameDetail?>, GameDetailUseCase.Params>() {

    data class Params(val id: Int)

    override suspend fun run(params: Params): ResultState<GameDetail?> {
        return repository.getGameDetail(params.id)
    }

}