package com.peterstaranchuk.heroes_catalog.view

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.peterstaranchuk.common.HttpKeys
import com.peterstaranchuk.heroes_catalog.BuildConfig
import com.peterstaranchuk.heroes_catalog.R
import com.peterstaranchuk.heroes_catalog.presentation.ComicsPresentation

@Composable
fun ItemComics(comicsPresentation: ComicsPresentation) {
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
        Spacer(modifier = Modifier.height(2.dp))
        Image(
            painter = rememberImagePainter(data = url.toString()),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .defaultMinSize(40.dp, 40.dp)
        )

        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Black)) {
                append(stringResource(R.string.item_comics_title))
                append(": ")
            }
            withStyle(style = SpanStyle(color = colorResource(id = R.color.black_50_opacity))) {
                append(comicsPresentation.title)
            }
        }, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append(stringResource(R.string.item_comics_description))
                    append(": ")
                }
                withStyle(style = SpanStyle(color = colorResource(id = R.color.black_50_opacity))) {
                    append(comicsPresentation.description ?: stringResource(R.string.item_comics_empty_description_info))
                }
                if(!isExpanded) {
                    append("...")
                }
            },
            maxLines = if (isExpanded) Int.MAX_VALUE else 1,
            fontSize = 14.sp
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun Preview() {
    ItemComics(
        comicsPresentation = ComicsPresentation(
            id = 1,
            imageUrl = "url",
            title = "Title",
            description = "Description"
        )
    )
}