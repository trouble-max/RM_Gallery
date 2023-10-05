package com.example.rickandmortygallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rickandmortygallery.feature_rmdetail.CharacterDetailScreen
import com.example.rickandmortygallery.feature_rmlist.RmListScreen
import com.example.rickandmortygallery.ui.theme.RickAndMortyGalleryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyGalleryTheme {
                val windowSize = calculateWindowSizeClass(activity = this)
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "rm_list_screen"
                ) {
                    composable("rm_list_screen") {
                        RmListScreen(windowSize = windowSize, navController = navController)
                    }
                    composable(
                        "rm_detail_screen/{id}/{characterName}",
                        arguments = listOf(
                            navArgument("id") {
                                type = NavType.IntType
                            },
                            navArgument("characterName") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val id = remember {
                            it.arguments?.getInt("id")
                        }
                        val characterName = remember {
                            it.arguments?.getString("characterName")
                        }
                        CharacterDetailScreen(
                            id = id ?: 0,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}