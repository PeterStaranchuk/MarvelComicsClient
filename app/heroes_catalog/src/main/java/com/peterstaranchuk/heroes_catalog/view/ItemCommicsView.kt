package com.peterstaranchuk.heroes_catalog.view

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.peterstaranchuk.common.HttpKeys
import com.peterstaranchuk.heroes_catalog.BuildConfig
import com.peterstaranchuk.heroes_catalog.presentation.ComicsPresentation

@Composable
fun ItemComicsView(comicsPresentation: ComicsPresentation) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(Modifier.clickable {
        isExpanded = !isExpanded
    }) {
        val url = Uri.parse(comicsPresentation.imageUrl)
            .buildUpon()
            .appendQueryParameter(HttpKeys.API_KEY, BuildConfig.API_KEY)
            .appendQueryParameter(HttpKeys.TIMESTAMP, BuildConfig.TIMESTAMP)
            .appendQueryParameter(HttpKeys.HASH, BuildConfig.HASH.lowercase())
            .build()

        Image(
            painter = rememberImagePainter(data = url.toString()),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .defaultMinSize(100.dp, 100.dp)
        )
        Text(text = comicsPresentation.title)
        Text(
            text = comicsPresentation.description ?: "",
            maxLines = if (isExpanded) Int.MAX_VALUE else 1
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Preview() {
    ItemComicsView(
        comicsPresentation = ComicsPresentation(
            id = 1,
            imageUrl = "url",
            title = "Title",
            description = "Description"
        )
    )
}