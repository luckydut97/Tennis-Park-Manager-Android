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
    selectedItem: String,
    onNavigateToHome: () -> Unit,
    onNavigateToActivity: () -> Unit,
    onNavigateToEvent: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TennisGreen),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 상단 여백 및 체크인 카드
        Spacer(modifier = Modifier.height(40.dp))
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            CheckInCardPager()
        }

        // 하단 부분: 로고 + 결제 버튼
        Image(
            painter = painterResource(id = R.drawable.ic_typo_logo),
            contentDescription = "Tennis Park Logo",
            modifier = Modifier
                .width(474.dp)
                .height(43.28.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(24.dp))
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
        Spacer(modifier = Modifier.height(20.dp))
        // 하단 네비게이션 (네비게이션바 여백 자동 padding)
        BottomNavigationBar(
            selectedItem = selectedItem,
            onNavigateToHome = onNavigateToHome,
            onNavigateToActivity = onNavigateToActivity,
            onNavigateToEvent = onNavigateToEvent,
            onNavigateToSettings = onNavigateToSettings
        )
    }
}

@Preview(showBackground = true, widthDp = 1600, heightDp = 2560)
@Composable
fun TabletMainScreenPreview() {
    Tennispark_tabletTheme {
        MainScreen(
            selectedItem = "홈",
            onNavigateToHome = {},
            onNavigateToActivity = {},
            onNavigateToEvent = {},
            onNavigateToSettings = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 1600, heightDp = 2560)
@Composable
fun TabletSplashScreenPreview() {
    Tennispark_tabletTheme {
        SplashScreen(onSplashComplete = {})
    }
}
