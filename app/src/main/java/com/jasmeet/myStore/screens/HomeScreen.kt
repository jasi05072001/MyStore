@file:OptIn(ExperimentalMaterial3Api::class)

package com.jasmeet.myStore.screens

import android.widget.Toast
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jasmeet.myStore.R
import com.jasmeet.myStore.appComponents.ItemLayout
import com.jasmeet.myStore.data.Category
import com.jasmeet.myStore.db.data.CartItem
import com.jasmeet.myStore.db.data.Item
import com.jasmeet.myStore.navigation.AppRouter
import com.jasmeet.myStore.navigation.Screens
import com.jasmeet.myStore.ui.theme.robotoCondensedLight
import com.jasmeet.myStore.ui.theme.robotoRegular
import com.jasmeet.myStore.utils.parseJsonFromAssets
import com.jasmeet.myStore.viewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current

    val homeViewModel :HomeViewModel = hiltViewModel()
    val cartItemCount by homeViewModel.cartItems.collectAsState(initial = emptyList())

    val listState = rememberLazyListState()

    val isVisible  by remember {
        derivedStateOf {
            !listState.isScrollInProgress
        }
    }

    val categories  = remember {
        parseJsonFromAssets(context)
    }

    val isExpanded = rememberSaveable {
        mutableStateOf(true)
    }

    val dialogList = listOf("Daily needs","Electronics","Fashion","Stationary")
    val deviceWidth = LocalConfiguration.current.screenWidthDp.dp

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
                        onClick = {
                            AppRouter.navigateTo(Screens.FavouritesScreen)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    BadgedBox(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable {
                                AppRouter.navigateTo(Screens.CartScreen)
                            },
                        badge = {
                            Badge(
                                containerColor = Color.Black,
                                contentColor = Color.White
                            ) {
                                Text(text = "${cartItemCount.size}")
                            }
                        }
                    ) {
                        Icon(
                            Icons.Outlined.ShoppingCart,
                            contentDescription = "Favorite",
                            tint = Color.Black,

                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ){
                Column (horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly){

                    AnimatedVisibility (
                        visible = !isExpanded.value,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {

                        Card(
                            modifier = Modifier

                                .padding(bottom = 15.dp)
                                .width(deviceWidth * 0.8f)
                                .wrapContentHeight()
                                .shadow(5.dp, RoundedCornerShape(10.dp), spotColor = Color.Black),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xffececec)
                            )
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(dialogList){
                                    Text(
                                        text = it,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = robotoRegular,
                                        color = Color.Black,
                                        modifier = Modifier.padding(10.dp)

                                    )
                                }
                            }
                        }
                    }
                    ExtendedFloatingActionButton(
                        containerColor = if (isExpanded.value) Color(0xff19191a) else Color(0xffff844c),
                        contentColor = Color.White,
                        shape =  if (isExpanded.value) RoundedCornerShape(18.dp) else  CircleShape,
                        text = {
                            Text(
                                text = "Categories",
                                fontFamily = robotoCondensedLight,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp
                            )
                        },
                        icon = {
                            Icon(
                                painter =
                                if (isExpanded.value) painterResource(id = R.drawable.categories) else painterResource(
                                    id = R.drawable.baseline_close_24
                                ),
                                contentDescription = null
                            )
                        },
                        onClick = {
                            isExpanded.value = !isExpanded.value
                        },
                        expanded = isExpanded.value,
                        modifier = Modifier
                    )
                }

            }

        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        CategoryList(
            categories = categories,
            paddingValues = paddingValues,
            listState = listState
        )

    }


}

@Composable
fun CategoryList(
    categories: List<Category>,
    paddingValues: PaddingValues,
    listState: LazyListState
) {
    LazyColumn(
        state = listState,
        modifier = Modifier
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
fun CategoryItem(
    category: Category,

    ) {

    val context = LocalContext.current
    val height = LocalConfiguration.current.screenHeightDp.dp/3.4f
    val isExpandedClicked = rememberSaveable {
        mutableStateOf(true)
    }
    val icon = if (isExpandedClicked.value) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp
    val iconBackGround = if (isExpandedClicked.value) Color(0xffF5F5F5) else Color(0xffE2E5DE)
    val  homeViewModel :HomeViewModel = hiltViewModel()

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
                                val items = Item(
                                    name = item.name,
                                    price = item.price,
                                    icon = item.icon,
                                )
                                homeViewModel.addItem(items)
                                Toast.makeText(context, "Item added to favourites", Toast.LENGTH_SHORT).show()

                            },
                            onQuantityClick = {
                                val items = CartItem(
                                    name = item.name,
                                    price = item.price,
                                    icon = item.icon,
                                    quantity = 1
                                )
                                homeViewModel.insertCartItem(items)
                            },

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

