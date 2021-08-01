package com.peterstaranchuk.heroes_catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.peterstaranchuk.common.BaseFragment
import com.peterstaranchuk.heroes_catalog.databinding.FragmentCatalogBinding

class HeroesCatalogFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentCatalogBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }
}