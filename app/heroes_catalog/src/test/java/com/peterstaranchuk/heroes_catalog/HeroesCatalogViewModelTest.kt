package com.peterstaranchuk.heroes_catalog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.peterstaranchuk.common.DataSource
import com.peterstaranchuk.common.DispatchersHolder
import com.peterstaranchuk.common.SourcedData
import com.peterstaranchuk.common.ViewVisibility
import com.peterstaranchuk.heroes_catalog.mappers.ComicsModelToPresentationMapper
import com.peterstaranchuk.heroes_catalog.model.HeroesCatalogErrors
import com.peterstaranchuk.heroes_catalog.model.ComicsModel
import com.peterstaranchuk.heroes_catalog.presentation.HeroesCatalogEvent
import com.peterstaranchuk.heroes_catalog.presentation.HeroesCatalogViewModel
import com.peterstaranchuk.marvelheroesapp.ui.theme.NetworkHelper
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.rules.TestRule

class HeroesCatalogViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var interactor: HeroesCatalogInteractor

    @MockK
    lateinit var networkHelper: NetworkHelper

    private val comicsModelToPresentationMapper = ComicsModelToPresentationMapper()

    private val dispatcher = TestCoroutineDispatcher()

    private val dispatchersHolder = DispatchersHolder(dispatcher, dispatcher)

    lateinit var vm: HeroesCatalogViewModel

    @Before
    fun before() {
        MockKAnnotations.init(this)
        vm = HeroesCatalogViewModel(interactor, comicsModelToPresentationMapper, dispatchersHolder, networkHelper)
    }

    @Test
    fun should_load_comics_list_when_screen_started() = runBlocking {
        coEvery { interactor.fetchComics() } returns getFakeComics()

        vm.onScreenStarted()

        coVerify {
            interactor.fetchComics()
        }

        Assert.assertEquals(3, vm.observeComics().value?.size)
    }

    @Test
    fun should_show_progress_when_screen_started() = runBlocking {
        coEvery { interactor.fetchComics() } coAnswers {
            delay(1000)
            getFakeComics()
        }

        vm.onScreenStarted()

        Assert.assertEquals(ViewVisibility.visible(), vm.progressVisibility)
    }

    @Test
    fun should_hide_progress_and_show_the_list_when_comics_loaded() = runBlockingTest {
        coEvery { interactor.fetchComics() } answers { getFakeComics() }

        vm.onScreenStarted()

        Assert.assertEquals(ViewVisibility.gone(), vm.progressVisibility)
    }

    @Test
    fun progress_bar_should_be_hidden_on_error() = runBlockingTest {
        coEvery { interactor.fetchComics() } throws RuntimeException("Something went wrong")

        vm.onScreenStarted()

        Assert.assertEquals(ViewVisibility.gone(), vm.progressVisibility)
    }

    @Test
    fun should_show_network_error_state_if_app_cannot_get_comics_because_of_internet_connection() = runBlockingTest {
        coEvery { interactor.fetchComics() } throws RuntimeException("Something went wrong")
        every { networkHelper.isUserConnectedToTheInternet() } returns false

        vm.onScreenStarted()

        Assert.assertEquals(HeroesCatalogErrors.NETWORK, vm.errorType.value)
    }

    @Test
    fun should_show_general_error_state_if_app_cannot_get_comics_because_of_unknown_reason()= runBlockingTest  {
        coEvery { interactor.fetchComics() } throws RuntimeException("Something went wrong")
        every { networkHelper.isUserConnectedToTheInternet() } returns true

        vm.onScreenStarted()

        Assert.assertEquals(HeroesCatalogErrors.GENERAL, vm.errorType.value)
    }

    @Test
    fun error_state_should_be_hidden_when_user_tap_reload() = runBlockingTest  {
        vm.errorStateVisibility.visible()

        coEvery { interactor.fetchComics() } returns getFakeComics()
        vm.onReloadButtonTaped()
        Assert.assertEquals(ViewVisibility.gone(), vm.errorStateVisibility)
    }

    @Test
    fun should_reload_screen_when_user_tap_reload_on_error_state() = runBlockingTest {
        coEvery { interactor.fetchComics() } returns getFakeComics()

        vm.onReloadButtonTaped()

        coVerify {
            interactor.fetchComics()
        }
    }

    @Test
    fun should_show_progress_bar_during_reload_on_error_state() = runBlockingTest {
        vm.progressVisibility.gone()
        coEvery { interactor.fetchComics() } coAnswers {
            delay(1000)
            getFakeComics()
        }

        vm.onReloadButtonTaped()
        Assert.assertEquals(ViewVisibility.visible(), vm.progressVisibility)
    }

    @Test
    fun should_hide_progress_bar_when_comics_fetched_after_reload() {
        coEvery { interactor.fetchComics() } returns getFakeComics()

        vm.onReloadButtonTaped()
        Assert.assertEquals(ViewVisibility.gone(), vm.progressVisibility)
    }

    @Test
    fun should_redirect_on_detail_view_when_comics_clicked() = runBlockingTest {
        vm.onComicsClicked(1L)

        Assert.assertEquals(1L, vm.observeDetailPageOpenEvent().value)
    }

    @Test
    fun should_redirect_on_the_search_view_when_user_tap_search() {
        vm.onSearchClicked()

        Assert.assertEquals(HeroesCatalogEvent.OPEN_SEARCH, vm.observeScreenEvent().value)
    }

    @Test
    fun should_redirect_on_series_page_when_series_icon_clicked_in_bottom_menu() {
        vm.onSeriesPageClicked()

        Assert.assertEquals(HeroesCatalogEvent.OPEN_SERIES_PAGE, vm.observeScreenEvent().value)
    }

    @Test
    fun should_redirect_on_creators_page_when_creators_icon_clicked_in_bottom_menu() {
        vm.onCreatorsPageClicked()

        Assert.assertEquals(HeroesCatalogEvent.OPEN_CREATORS_PAGE, vm.observeScreenEvent().value)
    }

    private fun getFakeComics() = SourcedData(
        listOf(
            ComicsModel(1, "", "", "", 0, 0, "", ""),
            ComicsModel(2, "", "", "", 0, 0, "", ""),
            ComicsModel(3, "", "", "", 0, 0, "", ""),
        ), DataSource.CLOUD
    )
}