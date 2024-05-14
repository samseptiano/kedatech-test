package com.example.rawg.ui.viewmodel

import android.content.Context
import com.example.rawg.BaseUnit
import com.example.rawg.base.data.ResponseWrapper
import com.example.rawg.base.data.ResultState
import com.example.rawg.base.testutils.MainCoroutinesRule
import com.example.rawg.data.modelMapper.GameItem
import com.example.rawg.data.repository.datasource.SampleDataSource
import com.example.rawg.domain.dao.FavoriteGameDao
import com.example.rawg.domain.database.FavoriteGameDatabase
import com.example.rawg.domain.usecase.sampleUseCase.GameFavoritAddUseCase
import com.example.rawg.domain.usecase.sampleUseCase.GameFavoritItemUseCase
import com.example.rawg.domain.usecase.sampleUseCase.GameFavoritRemoveUseCase
import com.example.rawg.domain.usecase.sampleUseCase.GameFavoritUseCase
import com.example.rawg.domain.usecase.sampleUseCase.GameListUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class GameListViewModelTest : BaseUnit() {

    // Subject under test
    private lateinit var viewModel: GameListViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @MockK
    lateinit var sampleDataSource: SampleDataSource

    @MockK
    lateinit var favoriteGameDao: FavoriteGameDao

    @MockK
    lateinit var favoriteGameDatabase: FavoriteGameDatabase


    @MockK
    lateinit var useCase: GameListUseCase

    @MockK
    lateinit var useCaseFavorit: GameFavoritUseCase

    @MockK
    lateinit var useCaseAdd: GameFavoritAddUseCase

    @MockK
    lateinit var useCaseDelete: GameFavoritRemoveUseCase

    @MockK
    lateinit var useCaseItem: GameFavoritItemUseCase


    @MockK
    lateinit var context: Context

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = GameListViewModel(
            context,
            useCase,
            useCaseFavorit,
            useCaseAdd,
            useCaseDelete,
            useCaseItem
        )
    }

    @Test
    fun getListGame_test() {
        // given
        val page = 1
        val pageSize = 1
        val search = ""

        val mockData = mockk<ResponseWrapper<List<GameItem?>?>>(relaxed = true)
        val mockResponse = ResultState.Success(mockData)

        coEvery {
            useCase.run(GameListUseCase.Params(page, pageSize, search))
        } returns mockResponse

        // when
        runBlocking(Dispatchers.Default) {
            viewModel.getListGame(page, pageSize, search)
        }

        // then
        coVerify(exactly = 1) { useCase.run(GameListUseCase.Params(page, pageSize, search)) }

        Assert.assertEquals(
            mockResponse.data.results,
            viewModel.gameList.value
        )
    }
    

    @After
    fun tearDown() {

    }
}