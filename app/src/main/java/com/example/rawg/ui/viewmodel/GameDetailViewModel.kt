package com.example.rawg.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rawg.base.viewmodel.BaseViewModel
import com.example.rawg.data.modelMapper.GameDetail
import com.example.rawg.domain.usecase.sampleUseCase.GameDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


@HiltViewModel
class GameDetailViewModel @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val useCase: GameDetailUseCase
) : BaseViewModel() {
    private val _gameDetail = MutableLiveData<GameDetail?>()
    internal val gameDetail: LiveData<GameDetail?>
        get() = _gameDetail


    internal suspend fun getDetailGame(id: Int) {
        val param = GameDetailUseCase.Params(id)
        useCase.run(param).apply {
            onSuccess {
                _gameDetail.value = it
            }
        }
    }
}