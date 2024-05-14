package com.example.rawg.domain.service


import com.example.rawg.base.data.ResponseWrapper
import com.example.rawg.data.model.GameResponse
import com.example.rawg.utils.CONSTANTS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface SampleServices {
    @GET(Routes.GET_LIST_GAMES)
    suspend fun getGameList(
        @Query("key") apikey: String = CONSTANTS.API_KEY,
        @Query("page") page: Int? = null,
        @Query("page_size") pageSize: Int? = null,
        @Query("search") search: String? = null
    ): Response<ResponseWrapper<List<GameResponse?>?>>

    @GET(Routes.GET_DETAIL_GAMES)
    suspend fun getGameDetail(
        @Path("id") gameId: Int,
        @Query("key") apikey: String = CONSTANTS.API_KEY
    ): Response<GameResponse>
}