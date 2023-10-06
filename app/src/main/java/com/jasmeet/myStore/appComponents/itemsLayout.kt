package com.jasmeet.myStore.appComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jasmeet.myStore.data.Item
import com.jasmeet.myStore.ui.theme.robotoCondensedLight
import com.jasmeet.myStore.ui.theme.robotoRegular

@ExperimentalMaterial3Api
@Composable
fun ItemLayout(
    item: Item,
    iQuantityMeasurable: Boolean = false,
    onFavoritesClick: () -> Unit = {},
    onQuantityClick: () -> Unit = {}
) {

    val deviceHeight = LocalConfiguration.current.screenHeightDp.dp
    val deviceWidth = LocalConfiguration.current.screenWidthDp.dp

    val isFavouritesItemClicked = rememberSaveable {
        mutableStateOf(false)
    }


    val priceAnnotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontFamily = robotoRegular,
            )
        ){
            append("₹${item.price}")
        }
        if (iQuantityMeasurable) {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    fontFamily = robotoCondensedLight,
                )
            ){
                append(" /kg")
            }
        }

    }
    val icon = if (isFavouritesItemClicked.value) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder

    Card(
        modifier = Modifier
            .height(deviceHeight * 0.26f)
            .width(deviceWidth * 0.4f)
            .shadow(5.dp, RoundedCornerShape(10.dp), ambientColor = Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),

        ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {

            val (
                favouritesIcon,
                image,
                titleText,
                priceText,
                quantityIcon
            ) = createRefs()

            IconButton(
                onClick = {
                    isFavouritesItemClicked.value = !isFavouritesItemClicked.value
                    onFavoritesClick()
                },
                modifier = Modifier.constrainAs(favouritesIcon) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Favourite",
                    tint = if (isFavouritesItemClicked.value) Color.Red else Color.Black
                )
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.icon)
                    .crossfade(true)
                    .build(),
                contentDescription = "Item Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(deviceHeight * 0.1f)
                    .width(deviceWidth * 0.27f)
                    .constrainAs(image) {
                        top.linkTo(favouritesIcon.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Text(
                text = item.name,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontFamily = robotoRegular,
                modifier = Modifier
                    .padding(top = 20.dp, start = 15.dp, end = 8.dp)
                    .constrainAs(titleText) {
                        top.linkTo(image.bottom)
                        start.linkTo(parent.start)


                    },
                fontSize = 16.sp,
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis

            )
            Text(
                text = priceAnnotatedString,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = robotoRegular,
                modifier = Modifier
                    .padding(top = 5.dp,start = 15.dp, end = 8.dp)
                    .constrainAs(priceText) {
                        top.linkTo(titleText.bottom)
                        start.linkTo(parent.start)
                    },
                fontSize = 18.sp,
            )
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier
                    .padding(end = 8.dp, top = 5.dp)
                    .background(Color(0xffff844c), shape = RoundedCornerShape(7.dp))
                    .constrainAs(quantityIcon) {
                        top.linkTo(titleText.bottom)
                        end.linkTo(parent.end)
                    }
                    .padding(3.dp)
                    .clickable {
                        onQuantityClick()
                    }

            )

        }


    }

}
