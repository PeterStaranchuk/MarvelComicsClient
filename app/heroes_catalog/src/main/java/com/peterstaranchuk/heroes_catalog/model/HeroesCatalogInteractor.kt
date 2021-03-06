package com.peterstaranchuk.heroes_catalog

import com.peterstaranchuk.common.SourcedData
import com.peterstaranchuk.heroes_catalog.model.ComicsModel

interface HeroesCatalogInteractor {
    suspend fun fetchComics(): SourcedData<List<ComicsModel>>
}

class HeroesCatalogInteractorImpl(private val comicsRepository: ComicsRepository) : HeroesCatalogInteractor {

    override suspend fun fetchComics(): SourcedData<List<ComicsModel>> {
        return comicsRepository.fetchComics()
    }

}