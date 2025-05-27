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
            onNavigateToActivity = { currentScreen = "activity" },
            onNavigateToEvent = { currentScreen = "event" }
        )
        "activity" -> TabletActivityScreen(
            onNavigateBack = { currentScreen = "main" }
        )
        "event" -> TabletEventScreen(
            onNavigateBack = { currentScreen = "main" }
        )
    }
}