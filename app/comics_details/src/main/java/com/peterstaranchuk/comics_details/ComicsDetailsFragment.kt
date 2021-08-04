package com.peterstaranchuk.comics_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.peterstaranchuk.common.BaseFragment

class ComicsDetailsFragment : BaseFragment() {

    val comicsId by lazy { getDeeplink().getQueryParameter("COMICS_ID") }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Text(text = "Comics details $comicsId", color = Color.White)
            }
        }
    }
}

fun Fragment.getDeeplink(): Uri {
    val intent = arguments?.get("android-support-nav:controller:deepLinkIntent") as Intent
    return intent.data ?: throw IllegalStateException("deeplink have to contain comics id")
}