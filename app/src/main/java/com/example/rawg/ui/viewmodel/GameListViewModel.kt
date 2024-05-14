package com.example.rawg.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rawg.base.viewmodel.BaseViewModel
import com.example.rawg.data.modelMapper.GameItem
import com.example.rawg.data.roomModel.RoomGameDetail
import com.example.rawg.domain.usecase.sampleUseCase.GameFavoritAddUseCase
import com.example.rawg.domain.usecase.sampleUseCase.GameFavoritItemUseCase
import com.example.rawg.domain.usecase.sampleUseCase.GameFavoritRemoveUseCase
import com.example.rawg.domain.usecase.sampleUseCase.GameFavoritUseCase
import com.example.rawg.domain.usecase.sampleUseCase.GameListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class GameListViewModel @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val useCase: GameListUseCase,
    private val useCaseFavorit: GameFavoritUseCase,
    private val useCaseFavoritAdd: GameFavoritAddUseCase,
    private val useCaseFavoritDelete: GameFavoritRemoveUseCase,
    private val useCaseFavoritItem: GameFavoritItemUseCase
) : BaseViewModel() {
    private val _gameList = MutableLiveData<List<GameItem?>?>()
    internal val gameList: LiveData<List<GameItem?>?>
        get() = _gameList

    private val _gameFavoritList = MutableLiveData<List<RoomGameDetail?>?>()
    internal val gameFavoritList: LiveData<List<RoomGameDetail?>?>
        get() = _gameFavoritList


    internal suspend fun getListGame(page: Int? = null, pageSize: Int? = null, search: String? = null) {
        val param = GameListUseCase.Params(page, pageSize, search)
        useCase.run(param).apply {
            onSuccess {
                _gameList.value = it.results
            }
        }
    }

    internal suspend fun getListGameFavorit() {
        useCaseFavorit.run(GameFavoritUseCase.Params()).collect {
            _gameFavoritList.value = it
        }
    }

    internal suspend fun addGameToFavorit(gameItem: GameItem) {
        useCaseFavoritAdd.run(GameFavoritAddUseCase.Params(gameItem))
    }

    internal suspend fun removeGameToFavorit(gameItem: GameItem) {
        useCaseFavoritDelete.run(GameFavoritRemoveUseCase.Params(gameItem))
    }

    internal suspend fun getGameFavoritById(id: Int): Any {
        return useCaseFavoritItem.run(GameFavoritItemUseCase.Params(id))
    }
}