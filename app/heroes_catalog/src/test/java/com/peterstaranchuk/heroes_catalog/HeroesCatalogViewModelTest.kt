package com.peterstaranchuk.heroes_catalog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.peterstaranchuk.common.DataSource
import com.peterstaranchuk.common.SourcedData
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
}