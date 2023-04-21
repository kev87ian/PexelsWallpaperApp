package com.kev.pexelswallpapers.screens.photo_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ImageDetailsScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxHeight()) {
            val context = LocalContext.current
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data("photo.src.portrait")
                    .crossfade(true)
                    .build(),
                contentDescription = "photo.alt"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    Button(onClick = {
                        /* downloadImage(context, imageDetailsResponse.links.download)*/
                    }) {
                        Text(text = "Download Image")
                    }

                    Button(onClick = {
                    }) {
                        Text(text = "Set Image as Wallpaper")
                    }
                }
            }
        }
    }
}
