package com.kev.pexelswallpapers.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kev.pexelswallpapers.R
import com.kev.pexelswallpapers.model.photos_list.Photo
import com.kev.pexelswallpapers.navigation.Screen

@Composable
fun HomeScreenListContent(
    items: LazyPagingItems<Photo>,
    navController: NavController
) {
    val photosViewModel = hiltViewModel<PhotosViewModel>()
    val photos = photosViewModel.getCuratedImages().collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(top = 16.dp),
        contentPadding = PaddingValues(all = 8.dp)
    ) {
        items(
            items = items,
            key = { photo ->
                photo.id
            }
        ) { photo ->
            photo?.let { PhotoItem(photo, navController) }
        }

        val loadState = photos.loadState.mediator
        item {
            if (loadState?.refresh == LoadState.Loading) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 20.dp),
                        text = "Fetching Data"
                    )

                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }

            if (loadState?.append == LoadState.Loading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }

            if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
                val isPaginatingError =
                    (loadState.append is LoadState.Error) || photos.itemCount > 1
                val error = if (loadState.append is LoadState.Error) {
                    (loadState.append as LoadState.Error).error
                } else {
                    (loadState.refresh as LoadState.Error).error
                }

                val modifier = if (isPaginatingError) {
                    Modifier.padding(8.dp)
                } else {
                    Modifier.fillParentMaxSize()
                }
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!isPaginatingError) {
                        Icon(
                            modifier = Modifier
                                .size(64.dp),
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = null
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = if (error.localizedMessage.contains("Unable to resolve host")) "Ensure you have an active internet connection" else error.localizedMessage,
                        textAlign = TextAlign.Center
                    )

                    Button(
                        onClick = {
                            photos.refresh()
                        },
                        content = {
                            Text(text = "Refresh")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoItem(
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
