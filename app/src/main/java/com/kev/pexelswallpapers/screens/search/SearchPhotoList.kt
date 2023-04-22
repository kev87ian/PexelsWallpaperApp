package com.kev.pexelswallpapers.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kev.pexelswallpapers.R
import com.kev.pexelswallpapers.model.photo_search.Photo
import com.kev.pexelswallpapers.model.photo_search.PhotoSearchResponse
import com.kev.pexelswallpapers.navigation.Screen
import com.kev.pexelswallpapers.util.Resource

@Composable
fun SearchPhotoList(
    navController: NavController
) {
    val viewModel: SearchPhotoViewModel = hiltViewModel()
    val photosResult = viewModel.searchPhotosStateFlow.collectAsState()

    when (photosResult.value) {
        is Resource.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(imageVector = Icons.Default.Warning, contentDescription = "Warning Icon")
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = (photosResult.value as Resource.Error<PhotoSearchResponse>).errorMessage
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = {
                    /* viewModel.searchPhotosStateFlow.collectAsState()*/
                }) {
                    Text(text = "Retry")
                }
            }
        }
        is Resource.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        is Resource.Success -> {
            val photos = (photosResult.value as Resource.Success<PhotoSearchResponse>).data
            photos.photos.forEach {
                SearchPhotoItem(photo = it, navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPhotoItem(
    photo: Photo,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(400.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            navController.navigate(Screen.Details.wihArgs(photo.id))
        }
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photo.src.large2x)
                    .placeholder(R.drawable.ic_placeholder)
                    .crossfade(1000)
                    .build(),
                contentScale = ContentScale.FillBounds,
                contentDescription = photo.alt,
                modifier = Modifier.fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = photo.photographer,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}
