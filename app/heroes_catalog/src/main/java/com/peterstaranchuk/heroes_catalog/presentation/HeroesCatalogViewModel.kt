package com.peterstaranchuk.heroes_catalog.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterstaranchuk.heroes_catalog.mappers.ComicsModelToPresentationMapper
import com.peterstaranchuk.heroes_catalog.HeroesCatalogInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HeroesCatalogViewModel(
    private val interactor: HeroesCatalogInteractor,
    private val comicsModelToPresentationMapper: ComicsModelToPresentationMapper
) : ViewModel() {

    private val comics = MutableLiveData<List<ComicsPresentation>>()

    fun onScreenStarted() {
        viewModelScope.launch {
            val comicsModels = interactor.fetchComics()
            val comicsPresentation = comicsModelToPresentationMapper.mapAll(comicsModels.data)
            viewModelScope.launch(Dispatchers.Main) {
                comics.value = comicsPresentation
            }
        }
    }

    fun observeComics() : LiveData<List<ComicsPresentation>> = comics
}