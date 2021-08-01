package com.peterstaranchuk.heroes_catalog

import com.peterstaranchuk.common.DataSource
import com.peterstaranchuk.common.SourcedData

interface ComicsRepository {
    suspend fun fetchComics(): SourcedData<List<ComicsModel>>
}

class ComicsRepositoryImpl(private val comicsApi: ComicsApi) : ComicsRepository {

    override suspend fun fetchComics(): SourcedData<List<ComicsModel>> {
        val response = comicsApi.getComics(100, 0)
        return SourcedData(response.data.results, DataSource.CLOUD)
    }

}