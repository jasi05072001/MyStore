package com.jasmeet.myStore.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jasmeet.myStore.R
import com.jasmeet.myStore.navigation.AppRouter
import com.jasmeet.myStore.navigation.Screens
import com.jasmeet.myStore.ui.theme.robotoCondensedLight
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {

    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.splash_animation)
    )

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f,
        restartOnPlay = false

    )

    val deviceHeight = LocalConfiguration.current.screenHeightDp.dp
    val deviceWidth = LocalConfiguration.current.screenWidthDp.dp


    val scale = remember {
        Animatable(initialValue = 0f)
    }

    LaunchedEffect(
        key1 = true,
        block = {
            scale.animateTo(
                targetValue = 0.95f,
                animationSpec = tween(
                    durationMillis = 1200,
                    easing = {
                        OvershootInterpolator(0.8f).getInterpolation(it)
                    }
                )
            )
            delay(1800)
            AppRouter.navigateTo(Screens.HomeScreen)


        }
    )

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfffab737)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ){
        @Suppress("DEPRECATION")
        Surface(
            shape = CircleShape,
            modifier = Modifier
                .wrapContentSize()
                .shadow(20.dp, CircleShape, ambientColor = Color.Black),
            tonalElevation = 90.dp,
            color = Color(0xFFFDD835)
        ) {
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier
                    .height(deviceHeight * 0.35f)
                    .width(deviceWidth * 0.7f)
                    .padding(10.dp),
                contentScale = ContentScale.FillBounds
            )
        }

        Spacer(modifier = Modifier.height(deviceHeight * 0.05f))

        Text(
            text = stringResource(id = R.string.app_name),
            color = Color.Black,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            fontFamily = robotoCondensedLight,
            fontWeight = FontWeight.W700,
            modifier = Modifier
                .scale(
                    scale.value*1.25f
                )
        )
    }
}

@Preview(device = Devices.PIXEL_4_XL)
@Composable
fun SplashScreenPreview() {
    SplashScreen()

}