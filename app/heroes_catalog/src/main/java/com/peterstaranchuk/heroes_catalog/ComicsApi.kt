package com.peterstaranchuk.heroes_catalog

import retrofit2.http.GET
import retrofit2.http.Query

interface ComicsApi {

    @GET("/v1/public/comics")
    suspend fun getComics(@Query("limit") limit: Int, @Query("offset") offset: Int) : List<ComicsModel>
}