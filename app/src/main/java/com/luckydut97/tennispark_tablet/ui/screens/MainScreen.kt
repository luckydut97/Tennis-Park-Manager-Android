package com.luckydut97.tennispark_tablet.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckydut97.tennispark_tablet.R
import com.luckydut97.tennispark_tablet.ui.components.BottomNavigationBar
import com.luckydut97.tennispark_tablet.ui.components.CheckInCardPager
import com.luckydut97.tennispark_tablet.ui.theme.*

@Composable
fun SplashScreen(onSplashComplete: () -> Unit) {
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2000)
        onSplashComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TennisGreen),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_typo_logo),
            contentDescription = "Tennis Park Logo",
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun MainScreen(
    onNavigateToActivity: () -> Unit = {},
    onNavigateToEvent: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TennisGreen)
    ) {
        // 메인 콘텐츠 영역
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 상단 여백
            Spacer(modifier = Modifier.height(40.dp))

            // Check-in Card Pager
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CheckInCardPager()
            }
        }

        // 하단 고정 요소들
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp), // 갤럭시 탭 기본 메뉴바 여백
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Tennis Park Logo
            Image(
                painter = painterResource(id = R.drawable.ic_typo_logo),
                contentDescription = "Tennis Park Logo",
                modifier = Modifier
                    .width(474.dp)
                    .height(43.28.dp),
                contentScale = ContentScale.Fit
            )

            // Payment Button
            Button(
                onClick = { /* Handle payment */ },
                modifier = Modifier
                    .width(474.dp)
                    .height(59.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFFF57),
                    contentColor = TennisGreen
                ),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "상품 결제",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // 하단 네비게이션과의 간격
            Spacer(modifier = Modifier.height(20.dp))
        }

        // 하단 네비게이션
        BottomNavigationBar(
            selectedItem = "홈",
            onNavigateToHome = { },
            onNavigateToActivity = onNavigateToActivity,
            onNavigateToEvent = onNavigateToEvent,
            onNavigateToSettings = { },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Preview(showBackground = true, widthDp = 1600, heightDp = 2560)
@Composable
fun TabletMainScreenPreview() {
    Tennispark_tabletTheme {
        MainScreen()
    }
}

@Preview(showBackground = true, widthDp = 1600, heightDp = 2560)
@Composable
fun TabletSplashScreenPreview() {
    Tennispark_tabletTheme {
        SplashScreen(onSplashComplete = {})
    }
}