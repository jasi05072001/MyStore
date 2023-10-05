package com.jasmeet.myStore.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * Created by Jasmeet Singh on 28/03/2021
 * */

sealed class Screens{

    object SplashScreen :Screens()
    object HomeScreen :Screens()
    object FavouritesScreen :Screens()

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