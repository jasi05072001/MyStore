@file:Suppress("DEPRECATION")

package com.jasmeet.myStore.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jasmeet.myStore.R
import com.jasmeet.myStore.db.data.Item
import com.jasmeet.myStore.navigation.AppRouter
import com.jasmeet.myStore.navigation.Screens
import com.jasmeet.myStore.navigation.SystemBackButtonHandler
import com.jasmeet.myStore.ui.theme.robotoRegular
import com.jasmeet.myStore.viewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen() {
    val homeViewModel : HomeViewModel = hiltViewModel()

    val favouritesItems by homeViewModel.allItems.collectAsState(emptyList())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.background(Color(0xffececec)),
                title = {
                    Text(
                        text = "Favourite",
                        fontFamily = robotoRegular,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xffececec)
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            AppRouter.navigateTo(Screens.HomeScreen)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            )
        }
    ){paddingValues ->
        if (favouritesItems.isNotEmpty()) {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xffececec))
            ) {
                items(favouritesItems) {
                    FavouritesItem(items = it, homeViewModel = homeViewModel)
                }

            }
        }
        else{
            NoItemLayout()
        }

    }
    SystemBackButtonHandler {
        AppRouter.navigateTo(Screens.HomeScreen)
    }



}

@Composable
fun NoItemLayout() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.empty)
    )
    val deviceHeight = LocalConfiguration.current.screenHeightDp.dp

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f,
        restartOnPlay = false

    )

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier
                .height(deviceHeight * 0.35f)
                .padding(10.dp),
        )

        Text(
            text = "No Favourites",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = robotoRegular,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            color = Color.Black,
            textAlign = TextAlign.Center
        )

    }
}

@Composable
fun FavouritesItem(items: Item, homeViewModel: HomeViewModel) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 15.dp)
            .fillMaxWidth()
            .height(85.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = items.icon,
                contentDescription = null,
                modifier = Modifier.padding(16.dp)
            )
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(10.dp),
            ) {
                Text(
                    text = items.name,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row {

                    Text(
                        text = "Quantity: ${items.quantity}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = robotoRegular,
                                color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "₹${items.price}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = robotoRegular,
                        color = Color.Black
                    )
                }

            }


            Spacer(modifier =Modifier.weight(1f))

            Column (
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier
                        .clickable {
                            homeViewModel.deleteItem(items)

                        }
                )

                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier
                        .background(Color(0xffff844c), shape = RoundedCornerShape(7.dp))
                        .clickable {
                            homeViewModel.updateItem(items.copy(quantity = items.quantity + 1))
                        }

                )
            }
        }

    }
}