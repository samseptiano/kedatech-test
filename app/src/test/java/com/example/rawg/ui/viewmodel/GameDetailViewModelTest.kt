package com.example.rawg.ui.viewmodel

import android.content.Context
import com.example.rawg.BaseUnit
import com.example.rawg.base.data.ResultState
import com.example.rawg.base.testutils.MainCoroutinesRule
import com.example.rawg.data.modelMapper.GameDetail
import com.example.rawg.data.repository.datasource.SampleDataSource
import com.example.rawg.domain.dao.FavoriteGameDao
import com.example.rawg.domain.database.FavoriteGameDatabase
import com.example.rawg.domain.usecase.sampleUseCase.GameDetailUseCase
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
class GameDetailViewModelTest : BaseUnit() {

    // Subject under test
    private lateinit var viewModel: GameDetailViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @MockK
    lateinit var useCase: GameDetailUseCase


    @MockK
    lateinit var context: Context

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = GameDetailViewModel(
            context,
            useCase
        )
    }

    @Test
    fun getDetailGame_test() {
        // given
        val id = 1


        val mockData = mockk<GameDetail>(relaxed = true)
        val mockResponse = ResultState.Success(mockData)

        coEvery {
            useCase.run(GameDetailUseCase.Params(id))
        } returns mockResponse

        // when
        runBlocking(Dispatchers.Default) {
            viewModel.getDetailGame(id)
        }

        // then
        coVerify(exactly = 1) { useCase.run(GameDetailUseCase.Params(id)) }

        Assert.assertEquals(
            mockResponse.data,
            viewModel.gameDetail.value
        )
    }
    

    @After
    fun tearDown() {

    }
}