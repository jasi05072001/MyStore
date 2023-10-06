package com.jasmeet.myStore.repository

import com.jasmeet.myStore.db.dao.CartDao
import com.jasmeet.myStore.db.dao.ItemsDao
import com.jasmeet.myStore.db.data.CartItem
import kotlinx.coroutines.flow.Flow

class CartRepository(
    private val cartDao: CartDao
) {
    suspend fun insertCartItem(cartItem: CartItem) {
        cartDao.addItemToCart(cartItem)
    }

    suspend fun updateCartItem(cartItem: CartItem) {
        cartDao.updateCartItem(cartItem)
    }

    suspend fun deleteCartItem(cartItem: CartItem) {
        cartDao.deleteCartItem(cartItem)
    }

    fun getAllCartItems(): Flow<List<CartItem>> {
        return cartDao.getAllCartItems()
    }
}