package com.peterstaranchuk.heroes_catalog

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val heroesCatalogModule: Module = module {
    factory<HeroesCatalogInteractor> { HeroesCatalogInteractorImpl() }
    viewModel { HeroesCatalogViewModel(get()) }
}
