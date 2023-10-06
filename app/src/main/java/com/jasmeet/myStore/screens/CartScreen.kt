package com.jasmeet.myStore.screens

import android.util.Log
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jasmeet.myStore.R
import com.jasmeet.myStore.db.data.CartItem
import com.jasmeet.myStore.navigation.AppRouter
import com.jasmeet.myStore.navigation.Screens
import com.jasmeet.myStore.ui.theme.robotoRegular
import com.jasmeet.myStore.utils.trimToLength
import com.jasmeet.myStore.viewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen() {

    val homeViewModel :HomeViewModel = hiltViewModel()
    val cartItems by homeViewModel.cartItems.collectAsState(initial = emptyList())
    val totalPrice = cartItems.sumOf { it.price * it.quantity }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.background(Color(0xffececec)),
                title = {
                    Text(
                        text = "Cart",
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

        if (cartItems.isNotEmpty()) {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xffececec))
            ) {
                items(cartItems) {
                    CartItem(items = it, homeViewModel = homeViewModel,totalPrice = totalPrice)
                }

            }
            Log.d("TotalPrice", "CartScreen:$totalPrice ")
        }


    }

}

@Composable
fun CartItem(items: CartItem, homeViewModel: HomeViewModel, totalPrice: Double) {

    var quantity by remember { mutableIntStateOf(items.quantity) }
    val originalItemName = remember { mutableStateOf(items.name) }
    val maxLength = 15

    val trimmedString = trimToLength(originalItemName.value, maxLength)

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
                    text = trimmedString,
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "₹${items.price}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = robotoRegular
                )


            }


            Spacer(modifier =Modifier.weight(1f))

            Column (
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.remove),
                        contentDescription = "Remove",
                        tint = Color.White,
                        modifier = Modifier
                            .background(Color(0xffff844c), shape = RoundedCornerShape(7.dp))
                            .clickable {
                                if (quantity > 1) {
                                    quantity--
                                    homeViewModel.updateCartItemQuantity(
                                        items.copy(quantity = quantity),
                                        quantity
                                    )
                                } else {
                                    homeViewModel.deleteCartItem(items)
                                }


                            }

                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "${items.quantity}", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, fontFamily = robotoRegular)
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "Add",
                        tint = Color.White,
                        modifier = Modifier
                            .background(Color(0xffff844c), shape = RoundedCornerShape(7.dp))
                            .clickable {
                                quantity++
                                homeViewModel.updateCartItemQuantity(
                                    items.copy(quantity = quantity),
                                    quantity
                                )
                            }

                    )
                }
                Text(text = "₹${items.price * items.quantity}", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, fontFamily = robotoRegular)

                //a text to show the total price of all the items


            }
        }

    }
}