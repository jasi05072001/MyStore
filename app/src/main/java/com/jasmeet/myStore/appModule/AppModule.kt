//package com.jasmeet.myStore.appModule
//
//import android.app.Application
//import android.content.Context
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@InstallIn(SingletonComponent::class)
//@Module
//object AppModule {
//
//    @Provides
//    @Singleton
//    fun providesContext(application: Application):Context{
//        return application.applicationContext
//    }
//}