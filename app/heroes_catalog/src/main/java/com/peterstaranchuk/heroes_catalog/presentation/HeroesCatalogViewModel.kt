package com.peterstaranchuk.heroes_catalog.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterstaranchuk.common.DispatchersHolder
import com.peterstaranchuk.common.SingleLiveEvent
import com.peterstaranchuk.common.ViewVisibility
import com.peterstaranchuk.heroes_catalog.HeroesCatalogInteractor
import com.peterstaranchuk.heroes_catalog.mappers.ComicsModelToPresentationMapper
import com.peterstaranchuk.heroes_catalog.model.HeroesCatalogErrors
import com.peterstaranchuk.marvelheroesapp.ui.theme.NetworkHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class HeroesCatalogViewModel(
    private val interactor: HeroesCatalogInteractor,
    private val comicsModelToPresentationMapper: ComicsModelToPresentationMapper,
    private val dispatchers : DispatchersHolder,
    private val networkHelper : NetworkHelper
) : ViewModel() {

    val progressVisibility = ViewVisibility.invisible()
    val errorStateVisibility = ViewVisibility.gone()
    val errorType = MutableStateFlow(HeroesCatalogErrors.NETWORK)

    private val comics = MutableStateFlow<List<ComicsPresentation>>(emptyList())
    private val detailPageToOpen = SingleLiveEvent<Long>()
    private val onScreenEvent = SingleLiveEvent<HeroesCatalogEvent>()

    fun onScreenStarted() {
        fetchComics()
    }

    fun onReloadButtonTaped() {
        errorStateVisibility.gone()
        fetchComics()
    }

    private fun fetchComics() {
        progressVisibility.visible()
        viewModelScope.launch(dispatchers.io) {
            try {
                val comicsModels = interactor.fetchComics()
                val comicsPresentation = comicsModelToPresentationMapper.mapAll(comicsModels.data)
                viewModelScope.launch(dispatchers.main) {
                    comics.value = comicsPresentation
                    progressVisibility.gone()
                }
            } catch (e: Exception) {
                Timber.e(e)
                progressVisibility.gone()
                if (networkHelper.isUserConnectedToTheInternet()) {
                    errorType.value = HeroesCatalogErrors.GENERAL
                } else {
                    errorType.value = HeroesCatalogErrors.NETWORK
                }
                errorStateVisibility.visible()
            }
        }
    }

    fun onComicsClicked(comicsId: Long) {
        detailPageToOpen.value = comicsId
    }

    fun onSearchClicked() {
        onScreenEvent.value = HeroesCatalogEvent.OPEN_SEARCH
    }

    fun onSeriesPageClicked() {
        onScreenEvent.value = HeroesCatalogEvent.OPEN_SERIES_PAGE
    }

    fun onCreatorsPageClicked() {
        onScreenEvent.value = HeroesCatalogEvent.OPEN_CREATORS_PAGE
    }

    fun observeComics() : StateFlow<List<ComicsPresentation>> = comics
    fun observeScreenEvent() : LiveData<HeroesCatalogEvent> = onScreenEvent
    fun observeDetailPageOpenEvent() : LiveData<Long> = detailPageToOpen
}