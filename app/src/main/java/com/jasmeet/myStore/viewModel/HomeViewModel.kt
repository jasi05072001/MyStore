package com.jasmeet.myStore.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasmeet.myStore.db.data.CartItem
import com.jasmeet.myStore.db.data.Item
import com.jasmeet.myStore.repository.CartRepository
import com.jasmeet.myStore.repository.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val itemsRepository: ItemsRepository,
    private val cartRepository: CartRepository
)
    :ViewModel() {


    // this list contains the favourite items
   val allItems:Flow<List<Item>> = itemsRepository.getAllItems().flowOn(Dispatchers.IO)

    // this list contains the cart items
    val cartItems: Flow<List<CartItem>> = cartRepository.getAllCartItems().flowOn(Dispatchers.IO)


    /**
     * These function are used to add items
     * to favourite list
     */
    fun addItem(item: Item){
        viewModelScope.launch {
            itemsRepository.addItem(item)
        }
    }

    fun updateItem(item: Item){
        viewModelScope.launch {
            itemsRepository.updateItem(item)
        }
    }
    fun deleteItem(item: Item){
        viewModelScope.launch {
            itemsRepository.deleteItem(item)
        }
    }


    /**
     * These function are used to add items
     * to cart list
     */

    fun insertCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            cartRepository.insertCartItem(cartItem)
        }
    }

    fun updateCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            cartRepository.updateCartItem(cartItem)
        }
    }

    fun deleteCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            cartRepository.deleteCartItem(cartItem)
        }
    }

    fun updateCartItemQuantity(cartItem: CartItem, newQuantity: Int) {
        viewModelScope.launch {
            cartItem.quantity = newQuantity
            cartRepository.updateCartItem(cartItem)
        }
    }
}