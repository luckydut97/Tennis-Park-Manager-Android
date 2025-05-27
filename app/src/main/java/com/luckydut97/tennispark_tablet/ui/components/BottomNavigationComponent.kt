package com.luckydut97.tennispark_tablet.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckydut97.tennispark_tablet.ui.theme.*

@Composable
fun BottomNavigationBar(
    selectedItem: String,
    onNavigateToHome: () -> Unit = {},
    onNavigateToActivity: () -> Unit = {},
    onNavigateToEvent: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = TennisGreen,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomNavItem(
                icon = Icons.Default.Home,
                text = "홈",
                selected = selectedItem == "홈",
                onClick = onNavigateToHome
            )
            BottomNavItem(
                icon = Icons.Default.FitnessCenter,
                text = "활동관리",
                selected = selectedItem == "활동관리",
                onClick = onNavigateToActivity
            )
            BottomNavItem(
                icon = Icons.Default.CalendarToday,
                text = "이벤트 관리",
                selected = selectedItem == "이벤트 관리",
                onClick = onNavigateToEvent
            )
            BottomNavItem(
                icon = Icons.Default.Settings,
                text = "설정",
                selected = selectedItem == "설정",
                onClick = onNavigateToSettings
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: ImageVector,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(120.dp)
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = if (selected) BottomNavSelected else BottomNavUnselected,
                modifier = Modifier.size(28.dp)
            )
        }
        Text(
            text = text,
            fontSize = 14.sp,
            color = if (selected) BottomNavSelected else BottomNavUnselected,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}