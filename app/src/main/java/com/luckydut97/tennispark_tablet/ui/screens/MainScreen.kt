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
import androidx.compose.ui.platform.LocalConfiguration
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
            modifier = Modifier.size(396.dp),
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
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    // 반응형 크기 계산
    val logoWidth = (screenWidth * 0.45f).coerceAtMost(540.dp)
        .coerceAtLeast(340.dp) // 0.4f에서 0.45f로 증가, 최대값도 증가
    val cardWidth = (screenWidth * 0.55f).coerceAtMost(474.dp).coerceAtLeast(320.dp) // 카드 가로 크기

    // 네비게이션바 높이 고려 (줄인 높이)
    val navBarHeight = 110.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // 전체 배경을 흰색으로
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(TennisGreen)
                .statusBarsPadding()
                .padding(bottom = navBarHeight), // 네비게이션바 공간 확보
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // 중앙 정렬 추가
        ) {
            // 체크인 카드 영역
            CheckInCardPager()

            // 카드와 로고 사이 여백
            Spacer(modifier = Modifier.height(40.dp)) // 30dp에서 40dp로 증가

            // 테니스파크 로고
            Image(
                painter = painterResource(id = R.drawable.ic_typo_logo),
                contentDescription = "Tennis Park Logo",
                modifier = Modifier
                    .width(logoWidth)
                    .aspectRatio(474f / 43.28f), // 원본 비율 유지
                contentScale = ContentScale.Fit
            )

            // 로고와 버튼 사이 여백
            Spacer(modifier = Modifier.height(32.dp)) // 24dp에서 32dp로 증가

            // 상품 결제 버튼 - 카드 가로 크기와 동일
            Button(
                onClick = { /* Handle payment */ },
                modifier = Modifier
                    .width(cardWidth) // 카드 가로 크기와 동일
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
        }

        // 하단 네비게이션 - 절대 위치로 하단에 고정
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.White) // 네비게이션바 배경과 동일
                .navigationBarsPadding()
        ) {
            BottomNavigationBar(
                selectedItem = selectedItem,
                onNavigateToHome = onNavigateToHome,
                onNavigateToActivity = onNavigateToActivity,
                onNavigateToEvent = onNavigateToEvent,
                onNavigateToSettings = onNavigateToSettings
            )
        }
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
