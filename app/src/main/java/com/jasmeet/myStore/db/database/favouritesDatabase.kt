package com.jasmeet.myStore.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jasmeet.myStore.db.dao.ItemsDao
import com.jasmeet.myStore.db.data.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemsRoomDatabase: RoomDatabase() {
    abstract fun itemsDao(): ItemsDao

    companion object {
        @Volatile
        private var INSTANCE: ItemsRoomDatabase? = null

        fun getInstance(context: Context): ItemsRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ItemsRoomDatabase::class.java,
                        "items_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}