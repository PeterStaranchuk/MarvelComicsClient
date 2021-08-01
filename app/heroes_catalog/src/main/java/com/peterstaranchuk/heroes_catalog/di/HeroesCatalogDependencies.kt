package com.peterstaranchuk.heroes_catalog

import com.peterstaranchuk.heroes_catalog.mappers.ComicsModelToPresentationMapper
import com.peterstaranchuk.heroes_catalog.model.ComicsApi
import com.peterstaranchuk.heroes_catalog.presentation.HeroesCatalogViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val heroesCatalogModule: Module = module {
    factory<HeroesCatalogInteractor> { HeroesCatalogInteractorImpl(get()) }
    factory<ComicsRepository> { ComicsRepositoryImpl(get()) }
    factory { ComicsModelToPresentationMapper() }
    factory<ComicsApi> {
        val retrofit: Retrofit = get()
        retrofit.create(ComicsApi::class.java)
    }
    viewModel { HeroesCatalogViewModel(get(), get()) }
}
