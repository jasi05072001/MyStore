package com.jasmeet.myStore.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jasmeet.myStore.R
import com.jasmeet.myStore.appComponents.ItemLayout
import com.jasmeet.myStore.ui.theme.robotoCondensedLight
import com.jasmeet.myStore.ui.theme.robotoRegular
import com.jasmeet.myStore.viewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val isExpandedClicked = rememberSaveable {
        mutableStateOf(true)
    }
    val expandedIcon = if (isExpandedClicked.value) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp

    val i = rememberSaveable {
        mutableStateOf(0)

    }
    val context = LocalContext.current

    val productViewModel: HomeViewModel = HomeViewModel()

    var categories  = productViewModel.parseJson(context)


    LaunchedEffect(Unit) {
        categories = listOf(productViewModel.loadProductData(context = context))
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
                                Text(
                                    text = "${i.value}",

                                    )
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

        LazyColumn(
            modifier = Modifier
                .background(Color(0xfffcfeff))
                .padding(horizontal = 10.dp)
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            item() {

                Column {
                    Row(
                        Modifier
                            .padding(
                                start = 10.dp,
                                end = 10.dp,
                                top = 15.dp,
                                bottom = 5.dp,
                            )
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement . SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text ="" ,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            fontFamily = robotoRegular,
                            modifier = Modifier,
                            fontSize = 18.sp
                        )
                        Surface(
                            shape = CircleShape,
                            color = Color(0x4DB2BEB5),
                            tonalElevation = 10.dp,
                            modifier = Modifier
                        ) {
                            Icon(
                                imageVector = expandedIcon,
                                contentDescription = "Expand",
                                tint = Color.Black,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .clickable {
                                        isExpandedClicked.value = !isExpandedClicked.value
                                        i.value = i.value + 1
                                    }
                            )
                        }

                    }
                }
                if (isExpandedClicked.value) {
                    Divider(
                        Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp, top = 5.dp),
                        color = Color(0xffe0e0e0)
                    )
                }

                AnimatedVisibility(
                    visible = isExpandedClicked.value,
                    enter = fadeIn()+ expandVertically(),
                    exit = fadeOut() + shrinkVertically(),
                    modifier = Modifier.padding(10.dp)

                ) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(10) {
                            ItemLayout(
                                title = it.toString(),
                                price = it.toString(),
                                imageUrl = "https://cdn-icons-png.flaticon.com/128/2405/2405479.png",
                                onQuantityClick = { /*TODO*/ },
                                onFavoritesClick = { /*TODO*/ }
                            )
                        }
                    }
                }

            }
        }
    }

    //https://www.c-sharpcorner.com/article/navigation-drawer-with-material-3-in-jetpack-compose/

}

@Preview(device = Devices.PIXEL_4_XL)
@Composable fun HomeScreenPreview() {
    HomeScreen()
}