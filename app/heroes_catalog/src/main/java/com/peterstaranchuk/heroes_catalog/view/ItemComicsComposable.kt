package com.peterstaranchuk.heroes_catalog.view

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.peterstaranchuk.common.HttpKeys
import com.peterstaranchuk.heroes_catalog.BuildConfig
import com.peterstaranchuk.heroes_catalog.R
import com.peterstaranchuk.heroes_catalog.presentation.ComicsPresentation


@Composable
fun ItemComics(comicsPresentation: ComicsPresentation, containerModifier: Modifier, onItemClicked : () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var backgroundColor by remember { mutableStateOf(Color.White) }
    var textColor by remember { mutableStateOf(Color.Black) }

    Column(containerModifier
        .clip(RoundedCornerShape(8.dp))
        .clickable {
            onItemClicked()
            isExpanded = !isExpanded
        }
        .background(backgroundColor)
    ) {
        val url = Uri.parse(comicsPresentation.imageUrl)
            .buildUpon()
            .appendQueryParameter(HttpKeys.API_KEY, BuildConfig.API_KEY)
            .appendQueryParameter(HttpKeys.TIMESTAMP, BuildConfig.TIMESTAMP)
            .appendQueryParameter(HttpKeys.HASH, BuildConfig.HASH.lowercase())
            .build()

        Spacer(
            modifier = Modifier
                .height(2.dp)
        )

        val bitmapState = remember { mutableStateOf<Bitmap?>(null) }
        Glide.with(LocalContext.current)
            .asBitmap()
            .load(url)
            .into(
                object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        bitmapState.value = resource
                        val palette = Palette.from(resource).generate()
                        val colorInt = palette.vibrantSwatch?.rgb ?: ContextCompat.getColor(context, android.R.color.white)
                        val textColorInt = palette.vibrantSwatch?.titleTextColor ?: ContextCompat.getColor(context, android.R.color.white)
                        backgroundColor = Color(colorInt)
                        textColor = Color(textColorInt)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })

        bitmapState.value?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(1f)
                    .defaultMinSize(40.dp, 40.dp))
        }

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = textColor)) {
                    append(stringResource(R.string.item_comics_title))
                    append(": ")
                }
                withStyle(style = SpanStyle(color = textColor)) {
                    append(comicsPresentation.title)
                }
            },
            fontSize = 14.sp,
            color = textColor,
            modifier = Modifier.padding(all = 4.dp),
            maxLines = if(isExpanded) Int.MAX_VALUE else 2
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = textColor)) {
                    append(stringResource(R.string.item_comics_description))
                    append(": ")
                }
                withStyle(style = SpanStyle(color = textColor)) {
                    append(comicsPresentation.description ?: stringResource(R.string.item_comics_empty_description_info))
                }
                if (!isExpanded) {
                    append("...")
                }
            },
            maxLines = if (isExpanded) Int.MAX_VALUE else 1,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
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
        ),
        Modifier,
        {}
    )
}