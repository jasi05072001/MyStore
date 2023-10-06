package com.jasmeet.myStore.utils

import android.content.Context
import com.google.gson.Gson
import com.jasmeet.myStore.data.Category
import com.jasmeet.myStore.data.ResponseData
import java.io.BufferedReader
import java.io.InputStreamReader

fun parseJsonFromAssets(context: Context):List<Category>{
    val json = try {
        val inputStream = context.assets.open("shopping.json")
        val reader = BufferedReader(InputStreamReader(inputStream))
        val text = reader.readText()
        inputStream.close()
        text
    }catch (e:Exception){
        e.printStackTrace()
        return emptyList()
    }
    return try {
        val gson = Gson()
        val responseData = gson.fromJson(json, ResponseData::class.java)
        responseData.categories
    }catch (e:Exception){
        e.printStackTrace()
        emptyList()
    }
}