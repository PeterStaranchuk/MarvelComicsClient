package com.peterstaranchuk.heroes_catalog.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.peterstaranchuk.common.BaseFragment
import com.peterstaranchuk.heroes_catalog.R
import com.peterstaranchuk.heroes_catalog.heroesCatalogModule
import com.peterstaranchuk.heroes_catalog.presentation.HeroesCatalogViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.inject

class HeroesCatalogFragment : BaseFragment() {

    private val vm: HeroesCatalogViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(heroesCatalogModule)
    }

    override fun onDestroy() {
        unloadKoinModules(heroesCatalogModule)
        super.onDestroy()
    }

    @ExperimentalFoundationApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val comics by vm.observeComics().collectAsState()
                LazyVerticalGrid(
                    content = {
                        items(count = comics.size) { index ->
                            ItemComics(
                                comicsPresentation = comics[index],
                                containerModifier = Modifier.padding(all = 8.dp),
                                onItemClicked = {
                                    val request = NavDeepLinkRequest.Builder
                                        .fromUri(getString(R.string.deeplink_comics_details).toUri())
                                        .build()

                                    findNavController(this@HeroesCatalogFragment).navigate(request)
                                }
                            )
                        }
                    },
                    cells = GridCells.Fixed(2)
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.onScreenStarted()
    }
}