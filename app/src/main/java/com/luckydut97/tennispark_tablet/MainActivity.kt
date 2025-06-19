package com.luckydut97.tennispark_tablet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.luckydut97.tennispark_tablet.ui.screens.SplashScreen
import com.luckydut97.tennispark_tablet.ui.screens.MainScreen
import com.luckydut97.tennispark_tablet.ui.screens.TabletActivityScreen
import com.luckydut97.tennispark_tablet.ui.screens.TabletEventScreen
import com.luckydut97.tennispark_tablet.ui.screens.SettingScreen
import com.luckydut97.tennispark_tablet.ui.theme.Tennispark_tabletTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 다크모드 비활성화
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        enableEdgeToEdge()

        // 시스템 바 색상 설정
        window.statusBarColor = Color.Transparent.toArgb()
        window.navigationBarColor = Color.White.toArgb()

        // 라이트 모드 아이콘 설정 (어두운 아이콘)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = false // 상태바는 녹색 배경이므로 밝은 아이콘
            isAppearanceLightNavigationBars = true // 네비게이션바는 흰색 배경이므로 어두운 아이콘
        }

        setContent {
            Tennispark_tabletTheme {
                TennisParkTabletApp()
            }
        }
    }
}

@Composable
fun TennisParkTabletApp() {
    var currentScreen by remember { mutableStateOf("splash") }

    when (currentScreen) {
        "splash" -> SplashScreen(
            onSplashComplete = { currentScreen = "main" }
        )
        "main" -> MainScreen(
            selectedItem = "홈",
            onNavigateToHome = { /* already on home */ },
            onNavigateToActivity = { currentScreen = "activity" },
            onNavigateToEvent = { currentScreen = "event" },
            onNavigateToSettings = { currentScreen = "settings" } // 화면 전환 추가
        )
        "activity" -> TabletActivityScreen(
            selectedItem = "활동 관리",
            onNavigateToHome = { currentScreen = "main" },
            onNavigateToActivity = { /* already on activity */ },
            onNavigateToEvent = { currentScreen = "event" },
            onNavigateToSettings = { currentScreen = "settings" } // 화면 전환 추가
        )
        "event" -> TabletEventScreen(
            selectedItem = "이벤트 관리",
            onNavigateToHome = { currentScreen = "main" },
            onNavigateToActivity = { currentScreen = "activity" },
            onNavigateToEvent = { /* already on event */ },
            onNavigateToSettings = { currentScreen = "settings" } // 화면 전환 추가
        )
        "settings" -> SettingScreen(
            onNavigateToHome = { currentScreen = "main" },
            onNavigateToActivity = { currentScreen = "activity" },
            onNavigateToEvent = { currentScreen = "event" },
            onNavigateToSettings = { /* 이미 설정 화면 */ }
        ) // settings 화면도 공통 레이아웃으로 연결
    }
}
