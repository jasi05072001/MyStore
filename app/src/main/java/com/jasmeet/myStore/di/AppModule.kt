package com.jasmeet.myStore.di

import android.content.Context
import com.jasmeet.myStore.db.dao.CartDao
import com.jasmeet.myStore.db.dao.ItemsDao
import com.jasmeet.myStore.db.database.CartDatabase
import com.jasmeet.myStore.db.database.ItemsRoomDatabase
import com.jasmeet.myStore.repository.CartRepository
import com.jasmeet.myStore.repository.ItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesFavouritesDatabase(@ApplicationContext context: Context): ItemsRoomDatabase {
        return ItemsRoomDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun providesFavouritesDao(database: ItemsRoomDatabase): ItemsDao {
        return database.itemsDao()
    }

    @Provides
    @Singleton
    fun providesFavouritesRepository(itemsDao: ItemsDao): ItemsRepository {
        return ItemsRepository(itemsDao)
    }

    @Provides
    @Singleton
    fun providesCartDatabase(@ApplicationContext context: Context): CartDatabase {
        return CartDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun providesCartDao(database: CartDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    @Singleton
    fun providesCartRepository(cartDao: CartDao): CartRepository {
        return CartRepository(cartDao)
    }
    
    

}