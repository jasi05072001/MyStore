@file:Suppress("DEPRECATION")

package com.jasmeet.myStore.screens

import android.content.Context
import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jasmeet.myStore.R
import com.jasmeet.myStore.db.data.CartItem
import com.jasmeet.myStore.navigation.AppRouter
import com.jasmeet.myStore.navigation.Screens
import com.jasmeet.myStore.navigation.SystemBackButtonHandler
import com.jasmeet.myStore.ui.theme.robotoRegular
import com.jasmeet.myStore.utils.trimToLength
import com.jasmeet.myStore.viewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen() {

    val homeViewModel :HomeViewModel = hiltViewModel()
    val cartItems by homeViewModel.cartItems.collectAsState(initial = emptyList())
    val totalPrice = cartItems.sumOf { it.price * it.quantity }

    val formattedPrice = String.format("%.2f", totalPrice)

    val discount = formattedPrice.toDouble()*0.4
    val discountString = String.format("%.2f", discount)

    val total = formattedPrice.toDouble() - discount
    val totalString = String.format("%.2f", total)

    val context = LocalContext.current

    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.empty_cart)
    )
    val deviceHeight = LocalConfiguration.current.screenHeightDp.dp

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f,
        restartOnPlay = false

    )


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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xffececec)),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(paddingValues)
                        .background(Color(0xffececec))
                ) {
                    items(cartItems) {
                        CartItem(items = it, homeViewModel = homeViewModel)
                    }

                }

                PriceSection(formattedPrice, discountString, totalString, context)

            }
        }
        else{
            EmptyCartLayout(composition, progress, deviceHeight)
        }
    }

    SystemBackButtonHandler {
        AppRouter.navigateTo(Screens.HomeScreen)
    }

}

@Composable
private fun EmptyCartLayout(
    composition: LottieComposition?,
    progress: Float,
    deviceHeight: Dp
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier
                .height(deviceHeight * 0.5f)
                .padding(10.dp),
        )
    }
}

@Composable
private fun PriceSection(
    formattedPrice: String,
    discountString: String,
    totalString: String,
    context: Context
) {
    Column(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
    ) {
        Surface(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
                .padding(15.dp),
            shape = RoundedCornerShape(15.dp),
            color = Color.White,
            shadowElevation = 5.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .padding(vertical = 7.dp)

            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Sub total",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = robotoRegular,
                        modifier = Modifier.padding(horizontal = 15.dp),
                        color = Color.Black
                    )
                    Text(
                        text = "₹$formattedPrice",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = robotoRegular,
                        modifier = Modifier.padding(horizontal = 15.dp),
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Discount",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = robotoRegular,
                        modifier = Modifier.padding(horizontal = 15.dp),
                        color = Color.Black
                    )
                    Text(
                        text = "₹${discountString}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = robotoRegular,
                        modifier = Modifier.padding(horizontal = 15.dp),
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Divider()

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Total",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = robotoRegular,
                        modifier = Modifier.padding(horizontal = 15.dp),
                        color = Color.Black
                    )
                    Text(
                        text = "₹${totalString}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = robotoRegular,
                        modifier = Modifier.padding(horizontal = 15.dp),
                        color = Color.Black
                    )
                }
            }
        }
        Button(
            onClick = {
                Toast.makeText(context, "CheckOut", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .padding(horizontal = 25.dp)
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xffff844c),
                            Color(0xfffb814a)
                        )
                    ),
                    shape = RoundedCornerShape(5.dp)
                ),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            Text(
                text = "Checkout",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = robotoRegular,
                modifier = Modifier.padding(5.dp),

            )
        }

    }
}

@Composable
fun CartItem(items: CartItem, homeViewModel: HomeViewModel) {

    var quantity by remember { mutableIntStateOf(items.quantity) }
    val originalItemName = remember { mutableStateOf(items.name) }
    val maxLength = 15

    val trimmedString = trimToLength(originalItemName.value, maxLength)

    val formattedPrice = String.format("%.2f",items.price * items.quantity)

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
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "₹${items.price}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = robotoRegular,
                    color = Color.Black
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
                    Text(
                        text = "${items.quantity}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = robotoRegular,
                        color = Color.Black
                    )
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
                Text(
                    text = "₹${formattedPrice}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = robotoRegular,
                    color = Color.Black
                )

                //a text to show the total price of all the items


            }
        }

    }
}