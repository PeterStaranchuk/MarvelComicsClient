package com.peterstaranchuk.heroes_catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.peterstaranchuk.common.BaseFragment
import com.peterstaranchuk.heroes_catalog.databinding.FragmentCatalogBinding
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.inject

class HeroesCatalogFragment : BaseFragment() {

    private val vm : HeroesCatalogViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(heroesCatalogModule)
    }

    override fun onDestroy() {
        unloadKoinModules(heroesCatalogModule)
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentCatalogBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.onScreenStarted()
    }
}