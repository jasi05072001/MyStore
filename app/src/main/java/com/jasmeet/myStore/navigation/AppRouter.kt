package com.jasmeet.myStore.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * Created by Jasmeet Singh on 28/03/2021
 * */

sealed class Screens{

    data object SplashScreen :Screens()
    data object HomeScreen :Screens()
    data object FavouritesScreen :Screens()

    data object CartScreen :Screens()

}

/**
 * A class which handles the navigation in the app
 * */

object AppRouter{
    var currentScreen: MutableState<Screens> = mutableStateOf(Screens.SplashScreen)

    fun navigateTo(destination: Screens){
        currentScreen.value = destination
    }
}