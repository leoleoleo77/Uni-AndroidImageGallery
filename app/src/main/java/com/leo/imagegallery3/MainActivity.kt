package com.leo.imagegallery3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.leo.imagegallery3.utils.GalleryDatabase
import com.leo.imagegallery3.views.GalleryScreen
import com.leo.imagegallery3.views.HomeScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Wipe all database data and repopulate it for the sake of simplicity/testing
        // In a real scenario I wouldn't design it like this, obviously :-)
        val db = GalleryDatabase(this).deleteAllData().populate()
        val paintingsList = db.getAllPaintings()

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "home_screen"
            ) {
                composable(route = "home_screen") {
                    HomeScreen(
                        paintingsList,
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
                            paintingsList,
                            navController
                        )
                    }
                }
            }
        }
    }
}


