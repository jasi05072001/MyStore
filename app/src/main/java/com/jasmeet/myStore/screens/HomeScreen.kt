@file:OptIn(ExperimentalMaterial3Api::class)

package com.jasmeet.myStore.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jasmeet.myStore.R
import com.jasmeet.myStore.appComponents.ItemLayout
import com.jasmeet.myStore.data.Category
import com.jasmeet.myStore.ui.theme.robotoCondensedLight
import com.jasmeet.myStore.utils.parseJsonFromAssets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current

    val categories  = remember {
        parseJsonFromAssets(context)
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehaviour.nestedScrollConnection)
            .background(Color.White),
        topBar = {
            TopAppBar(
                modifier = Modifier.background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFC907),
                            Color(0xFFB5A00F),
                        )
                    )
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = Color.Black,
                        fontFamily = robotoCondensedLight,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 23.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                scrollBehavior = scrollBehaviour,
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent,
                ),
                actions = {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    BadgedBox(modifier = Modifier.padding(end = 10.dp),
                        badge = {
                            Badge(
                                containerColor = Color.Black,
                                contentColor = Color.White
                            ) {
                                Text(text = "0")


                            }
                        }
                    ) {
                        Icon(
                            Icons.Outlined.ShoppingCart,
                            contentDescription = "Favorite",
                            tint = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
            )
        }
    ) { paddingValues ->
        CategoryList(
            categories = categories,
            paddingValues = paddingValues
        )
    }


}

@Composable
fun CategoryList(
    categories: List<Category>,
    paddingValues: PaddingValues
) {
    LazyColumn(modifier = Modifier
        .padding(paddingValues)
        .height(LocalConfiguration.current.screenHeightDp.dp)
        .background(Color(0xFFF5F5F5))) {
        items(categories){
            CategoryItem(category = it)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItem(category: Category) {
    val height = LocalConfiguration.current.screenHeightDp.dp/3.45f
    val isExpandedClicked = rememberSaveable {
        mutableStateOf(true)
    }
    val icon = if (isExpandedClicked.value) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp
    val iconBackGround = if (isExpandedClicked.value) Color(0xffF5F5F5) else Color(0xffE2E5DE)

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
                .clickable {
                    isExpandedClicked.value = !isExpandedClicked.value
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = category.name,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 25.sp,
                modifier = Modifier.padding(bottom = 12.dp),
                color = Color.Black,
                fontFamily = robotoCondensedLight
            )
            Surface(
                shape = CircleShape,
                shadowElevation = 5.dp,
                color = iconBackGround,
                modifier = Modifier
                    .clickable {
                        isExpandedClicked.value = !isExpandedClicked.value
                    }
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
        AnimatedVisibility(visible = isExpandedClicked.value,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            Column {


                Divider(color = Color(0xff808080), thickness = 1.dp)

                Spacer(modifier = Modifier.height(15.dp))



                LazyRow(
                    modifier = Modifier
                        .height(height),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(category.items) { item ->
                        ItemLayout(
                            item = item,
                            onFavoritesClick = {
                                Log.d("TAGEr", "CategoryItem: ${item.name}")
                            },
                            onQuantityClick = {
                                Log.d("TAGEr", "CategoryItem: ${item.price}")
                            }
                        )
                    }
                }
            }
        }

    }
}



@Preview(device = Devices.PIXEL_4_XL)
@Composable fun HomeScreenPreview() {
    HomeScreen()
}

