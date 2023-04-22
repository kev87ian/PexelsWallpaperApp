package com.kev.pexelswallpapers.screens.details

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.kev.pexelswallpapers.R
import com.kev.pexelswallpapers.model.photo_details.PhotoDetailsResponse
import com.kev.pexelswallpapers.util.ImageDownloader
import com.kev.pexelswallpapers.util.Resource
import kotlinx.coroutines.launch

@Composable
fun ImageDetailsScreen(
    photo: PhotoDetailsResponse
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
    ) {
        Box(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(photo.src.portrait)
                    .placeholder(R.drawable.ic_placeholder)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.FillBounds,

                contentDescription = photo.alt,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clipToBounds()
                    .align(Alignment.Center)

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
                        downloadImage(context, photo.src.portrait)
                    }) {
                        Text(text = "Download Image")
                    }

                    Button(onClick = {
                        coroutineScope.launch {
                            val bitmap = getBitmapFromUrl(context, photo.src.portrait)
                            val wallpaperManager = WallpaperManager.getInstance(context)
                            try {
                                wallpaperManager.setBitmap(bitmap)
                                Toast.makeText(context, "Wallpaper changed", Toast.LENGTH_SHORT)
                                    .show()
                            } catch (e: Exception) {

                                TODO("Handle wallpaper errors")
                                e.printStackTrace()
                            }
                        }
                    }) {
                        Text(text = "Set as Wallpaper")
                    }

                    Button(onClick = {
                        coroutineScope.launch {
                            val bitmap = getBitmapFromUrl(context, photo.src.portrait)
                            val wallpaperManager = WallpaperManager.getInstance(context)
                            try {
                                wallpaperManager.setBitmap(
                                    bitmap,
                                    null,
                                    true,
                                    WallpaperManager.FLAG_LOCK
                                )
                                Toast.makeText(
                                    context,
                                    "Lockscreen wallpaper changed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } catch (e: Exception) {
                                TODO("Handle wallpaper errors")
                                e.printStackTrace()
                            }
                        }
                    }) {
                        Text(text = "Set as Lockscreen")
                    }
                }
            }
        }
    }
}

@Composable
fun PhotoDetailsScreen(
    photoId: Int
) {
    val viewModel = hiltViewModel<PhotoDetailsViewModel>()

    viewModel.getPhotoDetails(photoId)
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
                    viewModel.getPhotoDetails(photoId)
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

suspend fun getBitmapFromUrl(context: Context, url: String): Bitmap {
    val loading = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(url)
        .build()

    val result = (loading.execute(request) as SuccessResult).drawable
    return (result as BitmapDrawable).bitmap
}

fun downloadImage(context: Context, imageUrl: String) {
    val downloader = ImageDownloader(context)
    downloader.downloadImage(imageUrl)
}
