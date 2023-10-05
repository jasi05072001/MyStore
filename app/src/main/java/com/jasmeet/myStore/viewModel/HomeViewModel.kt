package com.jasmeet.myStore.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json


class HomeViewModel:ViewModel() {

    private var productData: ProductCategory? = null

    suspend fun getProductData(context: Context): ProductCategory {
        if (productData == null) {
            productData = loadProductData(context)
        }
        return productData!!
    }

     suspend fun loadProductData(context: Context): ProductCategory {
        return withContext(Dispatchers.IO) {
            val jsonString = context.assets.open("shopping.json")
                .bufferedReader()
                .use { it.readText() }
            Json.decodeFromString(jsonString)
        }
    }
    //function that will parse the json file and return a list of categories using Gson
     fun parseJson(context: Context): List<ProductCategory> {
        val jsonString = context.assets.open("shopping.json")
            .bufferedReader()
            .use { it.readText() }
        return Gson().fromJson(jsonString, Array<ProductCategory>::class.java).toList()
    }
}