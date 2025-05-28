package com.luckydut97.tennispark_tablet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.luckydut97.tennispark_tablet.ui.screens.SplashScreen
import com.luckydut97.tennispark_tablet.ui.screens.MainScreen
import com.luckydut97.tennispark_tablet.ui.screens.TabletActivityScreen
import com.luckydut97.tennispark_tablet.ui.screens.TabletEventScreen
import com.luckydut97.tennispark_tablet.ui.theme.Tennispark_tabletTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
            onNavigateToSettings = { /* put settings here */ }
        )
        "activity" -> TabletActivityScreen(
            selectedItem = "활동관리",
            onNavigateToHome = { currentScreen = "main" },
            onNavigateToActivity = { /* already on activity */ },
            onNavigateToEvent = { currentScreen = "event" },
            onNavigateToSettings = { /* put settings here */ }
        )
        "event" -> TabletEventScreen(
            selectedItem = "이벤트 관리",
            onNavigateToHome = { currentScreen = "main" },
            onNavigateToActivity = { currentScreen = "activity" },
            onNavigateToEvent = { /* already on event */ },
            onNavigateToSettings = { /* put settings here */ }
        )
    }
}
