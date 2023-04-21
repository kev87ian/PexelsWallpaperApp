package com.kev.pexelswallpapers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.kev.pexelswallpapers.navigation.SetupNavGraph
import com.kev.pexelswallpapers.screens.home.HomeScreen
import com.kev.pexelswallpapers.ui.theme.PexelsWallpapersTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PexelsWallpapersTheme {
                // A surface container using the 'background' color from the theme

                SetupNavGraph(navController = rememberNavController())


            }
        }
    }
}
