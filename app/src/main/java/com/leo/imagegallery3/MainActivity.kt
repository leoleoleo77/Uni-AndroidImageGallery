package com.leo.imagegallery3

import com.leo.imagegallery3.views.GalleryScreen
import Painting
import PaintingPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.leo.imagegallery3.ui.theme.ImageGalleryTheme
import com.leo.imagegallery3.views.HomeScreen
import com.leo.imagegallery3.views.PreviewButton

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val paintingPreferences = PaintingPreferences(this)
        paintingPreferences.savePaintingsData(paintingPreferences.getPaintingsData())

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "home_screen"
            ) {
                composable(route = "home_screen") {
                    HomeScreen(
                        paintingPreferences.getPaintingsData(),
                        navController
                    )
                }
                composable(
                    route = "gallery_view/{paintingIndex}",
                    arguments = listOf(navArgument("paintingIndex") { type = NavType.IntType })
                ) { backStackEntry ->
                    val paintingIndex = backStackEntry.arguments?.getInt("paintingIndex")
                    paintingIndex?.let {
                        GalleryScreen(
                            it,
                            paintingPreferences.getPaintingsData(),
                            navController
                        )
                    }
                }
            }
        }
    }
}


