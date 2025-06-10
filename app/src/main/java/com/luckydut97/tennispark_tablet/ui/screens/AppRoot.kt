package com.luckydut97.tennispark_tablet.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.luckydut97.tennispark_tablet.ui.screens.MainScreen
import com.luckydut97.tennispark_tablet.ui.screens.TabletActivityScreen
import com.luckydut97.tennispark_tablet.ui.screens.TabletEventScreen

@Composable
fun AppRoot() {
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val selectedItem = when (navBackStackEntry.value?.destination?.route) {
        "activity" -> "활동 관리"
        "event" -> "이벤트 관리"
        else -> "홈"
    }

    NavHost(navController, startDestination = "home") {
        composable("home") {
            MainScreen(
                selectedItem = selectedItem,
                onNavigateToHome = { if (selectedItem != "홈") navController.navigate("home") { popUpTo("home") { inclusive = true } } },
                onNavigateToActivity = { if (selectedItem != "활동 관리") navController.navigate("activity") { popUpTo("home") } },
                onNavigateToEvent = { if (selectedItem != "이벤트 관리") navController.navigate("event") { popUpTo("home") } },
                onNavigateToSettings = { /* 설정 추가시 연결 */ }
            )
        }
        composable("activity") {
            TabletActivityScreen(
                selectedItem = selectedItem,
                onNavigateToHome = { navController.navigate("home") { popUpTo("home") { inclusive = true } } },
                onNavigateToActivity = { /* 이미 이 화면 */ },
                onNavigateToEvent = { navController.navigate("event") { popUpTo("home") } },
                onNavigateToSettings = { /* 설정 추가시 연결 */ }
            )
        }
        composable("event") {
            TabletEventScreen(
                selectedItem = selectedItem,
                onNavigateToHome = { navController.navigate("home") { popUpTo("home") { inclusive = true } } },
                onNavigateToActivity = { navController.navigate("activity") { popUpTo("home") } },
                onNavigateToEvent = { /* 이미 이 화면 */ },
                onNavigateToSettings = { /* 설정 추가시 연결 */ }
            )
        }
        // 필요시 설정 route 추가 가능
    }
}
