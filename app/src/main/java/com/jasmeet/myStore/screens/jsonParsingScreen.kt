package com.jasmeet.myStore.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

data class Category(val name: String, val items: List<Item>)

data class Item(val name: String, val icon: String, val price: Double)

@Composable
fun CategoryList(categories: List<Category>) {
    LazyColumn {
        items(categories){
            CategoryItem(category = it)
        }
    }
}

@Composable
fun CategoryItem(category: Category) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = category.name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow {
            items(category.items) { item ->
                ItemCard(item = item)
            }
        }
    }
}

@Composable
fun ItemCard(item: Item) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.icon)
                    .crossfade(true)
                    .build(),
                contentDescription = "Item Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier


            )

            Text(text = item.name, fontWeight = FontWeight.Bold)
            Text(text = "Price: $${item.price}")
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    // Parse JSON and convert it to a list of Category objects
    val categories = remember { parseJsonFromAssets( context,"shopping.json" ) }

    CategoryList(categories = categories)
}

@Preview
@Composable
fun PreviewCategoryList() {
    val context = LocalContext.current
    val categories = remember { parseJsonFromAssets( context,"shopping.json" )  }
    CategoryList(categories = categories)
}

@Preview
@Composable
fun PreviewCategoryItem() {
    val category = Category(
        name = "Food",
        items = listOf(
            Item("Potato Chips", "https://cdn-icons-png.flaticon.com/128/2553/2553691.png", 40.00),
            Item("Penne Pasta", "https://cdn-icons-png.flaticon.com/128/2553/2553691.png", 110.40),
            // Add more items as needed
        )
    )
    CategoryItem(category = category)
}

data class ResponseData(val categories: List<Category>)

fun parseJsonFromAssets(context: Context, fileName: String): List<Category> {
    val json = try {
        val inputStream = context.assets.open(fileName)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val text = reader.readText()
        inputStream.close()
        text
    } catch (e: Exception) {
        e.printStackTrace()
        return emptyList()
    }

    return try {
        val gson = Gson()
        val responseData = gson.fromJson(json, ResponseData::class.java)
        responseData.categories
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}