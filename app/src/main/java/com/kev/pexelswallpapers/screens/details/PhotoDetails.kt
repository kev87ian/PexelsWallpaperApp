package com.kev.pexelswallpapers.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kev.pexelswallpapers.model.photo_details.PhotoDetailsResponse
import com.kev.pexelswallpapers.util.Resource

@Composable
fun ImageDetailsScreen(
    photo: PhotoDetailsResponse
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxHeight()) {
            val context = LocalContext.current
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(photo.src.large)
                    .crossfade(true)
                    .build(),
                contentDescription = photo.alt
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

@Composable
fun PhotoDetailsScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<PhotoDetailsViewModel>()

    // setup UI observers
    val state = viewModel.photoDetailsStateFlow.collectAsState()
    when (state.value) {
        is Resource.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(72.dp)
                )
                Text(text = "Please wait...")
            }
        }

        is Resource.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier
                        .size(64.dp),
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = "Warning icon"
                )

                Text(text = (state.value as Resource.Error<PhotoDetailsResponse>).errorMessage)
                Button(onClick = {
                    viewModel.getPhotoDetails(1)
                }) {
                    Text(text = "Retry")
                }
            }
        }

        is Resource.Success -> {
            val photo = (state.value as Resource.Success<PhotoDetailsResponse>).data
            ImageDetailsScreen(photo = photo)
        }
    }
}

fun downloadImage() {
}