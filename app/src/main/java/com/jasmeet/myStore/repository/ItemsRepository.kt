package com.jasmeet.myStore.repository

import com.jasmeet.myStore.db.data.Item
import com.jasmeet.myStore.db.dao.ItemsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class ItemsRepository (
    private val itemsDao: ItemsDao
){

    suspend fun addItem(newItem: Item){
        itemsDao.addItem(newItem)
    }

    suspend fun deleteItem(item: Item){
        itemsDao.deleteItem(item)
    }

    fun getAllItems(): Flow<List<Item>> {
        return  itemsDao.getAllItems()
    }

    suspend fun updateItem(item: Item) {
        itemsDao.updateItem(item)
    }

}