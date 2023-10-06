package com.jasmeet.myStore.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jasmeet.myStore.db.data.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemsDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<Item>>

    @Update
    suspend fun updateItem(item: Item)

    @Query("SELECT * FROM items WHERE quantity > 0")
    fun getItemsInCart(): Flow<List<Item>>
}