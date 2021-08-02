package com.peterstaranchuk.heroes_catalog.view

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.request.ImageResult
import com.peterstaranchuk.common.HttpKeys
import com.peterstaranchuk.heroes_catalog.BuildConfig
import com.peterstaranchuk.heroes_catalog.presentation.ComicsPresentation

@Composable
fun ItemComicsView(comicsPresentation: ComicsPresentation) {
    Column {
        val url = Uri.parse(comicsPresentation.imageUrl)
            .buildUpon()
            .appendQueryParameter(HttpKeys.API_KEY, BuildConfig.API_KEY)
            .appendQueryParameter(HttpKeys.TIMESTAMP, BuildConfig.TIMESTAMP)
            .appendQueryParameter(HttpKeys.HASH, BuildConfig.HASH.lowercase())
            .build()

        Image(
            painter = rememberImagePainter(data = url.toString(), builder = {

                this.listener(object : ImageRequest.Listener {
                    override fun onCancel(request: ImageRequest) {
                        super.onCancel(request)
                    }

                    override fun onError(request: ImageRequest, throwable: Throwable) {
                        super.onError(request, throwable)
                    }

                    override fun onStart(request: ImageRequest) {
                        super.onStart(request)
                    }

                    override fun onSuccess(request: ImageRequest, metadata: ImageResult.Metadata) {
                        super.onSuccess(request, metadata)
                    }
                })
            }),
            contentDescription = null,
            modifier = Modifier
                .size(140.dp)
        )
        Text(text = comicsPresentation.title)
        Text(text = comicsPresentation.description ?: "")
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