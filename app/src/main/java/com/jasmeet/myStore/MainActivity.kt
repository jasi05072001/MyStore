package com.jasmeet.myStore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jasmeet.myStore.navigation.AppRouter
import com.jasmeet.myStore.navigation.Screens
import com.jasmeet.myStore.screens.FavouritesScreen
import com.jasmeet.myStore.screens.HomeScreen
import com.jasmeet.myStore.screens.MainScreen
import com.jasmeet.myStore.screens.SplashScreen
import com.jasmeet.myStore.ui.theme.MyStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyStoreTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}


/**
 * Main App that holds all the screens
 */

@Composable
fun MainApp() {

    Surface(
        Modifier.fillMaxSize()
    ) {
        val currentScreen = AppRouter.currentScreen

        Box(
            modifier = Modifier.fillMaxSize()
        ){

            val isSplashScreenVisible = currentScreen.value is Screens.SplashScreen
            val isHomeScreenVisible = currentScreen.value is Screens.HomeScreen
            val isFavouritesScreen = currentScreen.value is Screens.FavouritesScreen

            AnimatedVisibility(
                visible = isSplashScreenVisible,
                enter = fadeIn() ,
//                        + slideInHorizontally(initialOffsetX = { -it }),
                exit = fadeOut()
//                        + slideOutHorizontally(targetOffsetX = { it })
            ) {
                SplashScreen()
            }

            AnimatedVisibility(
                visible = isHomeScreenVisible,
                enter = fadeIn() ,
//                        + slideInHorizontally(initialOffsetX = { -it }),
                exit = fadeOut()
//                        + slideOutHorizontally(targetOffsetX = { it })
            ) {
//                HomeScreen()
                MainScreen()
            }
            AnimatedVisibility(
                visible = isFavouritesScreen,
                enter = fadeIn() ,
//                        + slideInHorizontally(initialOffsetX = { -it }),
                exit = fadeOut()
//                        + slideOutHorizontally(targetOffsetX = { it })
            ) {
                FavouritesScreen()
            }


        }

    }
}