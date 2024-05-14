package com.example.rawg.base.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rawg.data.repository.SampleDataSourceImpl
import com.example.rawg.domain.usecase.sampleUseCase.*
import com.example.rawg.ui.viewmodel.GameDetailViewModel
import com.example.rawg.ui.viewmodel.GameListViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(private val repository: SampleDataSourceImpl, @ApplicationContext private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(GameListViewModel::class.java) -> GameListViewModel(
                context,
                GameListUseCase(repository),
                GameFavoritUseCase(repository),
                GameFavoritAddUseCase(repository),
                GameFavoritRemoveUseCase(repository),
                GameFavoritItemUseCase(repository),
            ) as T
            modelClass.isAssignableFrom(GameDetailViewModel::class.java) -> GameDetailViewModel(
                context,
                GameDetailUseCase(repository)
            ) as T
            else -> throw IllegalArgumentException("Unknown viewModel class $modelClass")
        }
    }

}