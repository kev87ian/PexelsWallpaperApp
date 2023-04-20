package com.kev.pexelswallpapers.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    onSearchClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Pexels Wallpapers",
                color = MaterialTheme.colorScheme.primary
            )
        },

        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search ICon")
            }
        }

    )
}

@Preview
@Composable
fun HomePreview() {
    HomeTopBar {
//
    }
}
