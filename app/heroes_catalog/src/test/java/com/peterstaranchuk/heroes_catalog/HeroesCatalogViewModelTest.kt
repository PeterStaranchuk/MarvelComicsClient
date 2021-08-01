package com.peterstaranchuk.heroes_catalog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.peterstaranchuk.common.DataSource
import com.peterstaranchuk.common.SourcedData
import com.peterstaranchuk.heroes_catalog.mappers.ComicsModelToPresentationMapper
import com.peterstaranchuk.heroes_catalog.model.ComicsModel
import com.peterstaranchuk.heroes_catalog.presentation.HeroesCatalogViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule

class HeroesCatalogViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @MockK
    lateinit var interactor: HeroesCatalogInteractor

    private val comicsModelToPresentationMapper = ComicsModelToPresentationMapper()

    lateinit var vm: HeroesCatalogViewModel

    @Before
    fun before() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(mainThreadSurrogate)
        vm = HeroesCatalogViewModel(interactor, comicsModelToPresentationMapper)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun should_load_comics_list_when_screen_started() {
        coEvery { interactor.fetchComics() } returns SourcedData(listOf(
            ComicsModel(1, "", "", "", 0, 0, "", ""),
            ComicsModel(2, "", "", "", 0, 0, "", ""),
            ComicsModel(3, "", "", "", 0, 0, "", ""),
        ), DataSource.CLOUD)

        vm.onScreenStarted()

        coVerify {
            interactor.fetchComics()
        }

        Assert.assertEquals(3, vm.observeComics().value?.size)
    }

    @Test
    fun should_show_progress_when_screen_started() {
        Assert.assertEquals(true, false)
    }

    @Test
    fun should_hide_progress_and_show_the_list_when_comics_loaded() {
        Assert.assertEquals(true, false)
    }

    @Test
    fun should_show_network_error_state_if_app_cannot_get_comics_because_of_internet_connection() {
        Assert.assertEquals(true, false)
    }

    @Test
    fun should_show_general_error_state_if_app_cannot_get_comics_because_of_unknown_reason() {
        Assert.assertEquals(true, false)
    }

    @Test
    fun should_redirect_on_detail_view_when_comics_clicked() {
        Assert.assertEquals(true, false)
    }

    @Test
    fun should_redirect_on_the_search_view_when_user_tap_search() {
        Assert.assertEquals(true, false)
    }

    @Test
    fun should_redirect_on_series_page_when_series_icon_clicked_in_bottom_menu() {
        Assert.assertEquals(true, false)
    }

    @Test
    fun should_redirect_on_series_page_when_creators_icon_clicked_in_bottom_menu() {
        Assert.assertEquals(true, false)
    }

    @Test
    fun should_reload_screen_when_user_tap_reload_on_error_state() {
        Assert.assertEquals(true, false)
    }

    @Test
    fun should_show_progress_bar_during_reload_on_error_state() {
        Assert.assertEquals(true, false)
    }

    @Test
    fun should_hide_progress_bar_when_comics_fetched_after_reload() {
        Assert.assertEquals(true, false)
    }
}