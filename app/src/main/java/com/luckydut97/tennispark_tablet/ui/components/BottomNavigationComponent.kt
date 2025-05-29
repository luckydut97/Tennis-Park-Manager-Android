package com.luckydut97.tennispark_tablet.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.navigationBarsPadding
import com.luckydut97.tennispark_tablet.R
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
        modifier = modifier.fillMaxWidth().navigationBarsPadding(),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomNavItem(
                icon = if (selectedItem == "홈") painterResource(R.drawable.ic_home_active) else painterResource(R.drawable.ic_home_deactive),
                text = "홈",
                selected = selectedItem == "홈",
                onClick = onNavigateToHome
            )
            BottomNavItem(
                icon = if (selectedItem == "활동관리") painterResource(R.drawable.activity_active) else painterResource(R.drawable.activity_deactive),
                text = "활동관리",
                selected = selectedItem == "활동관리",
                onClick = onNavigateToActivity
            )
            BottomNavItem(
                icon = if (selectedItem == "이벤트 관리") painterResource(R.drawable.event_active) else painterResource(R.drawable.event_deactive),
                text = "이벤트 관리",
                selected = selectedItem == "이벤트 관리",
                onClick = onNavigateToEvent
            )
            BottomNavItem(
                icon = if (selectedItem == "설정") painterResource(R.drawable.ic_setting_active) else painterResource(R.drawable.ic_setting_deactive),
                text = "설정",
                selected = selectedItem == "설정",
                onClick = onNavigateToSettings
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: Painter,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val selectedColor = Color(0xFF1C7756)
    val unselectedColor = Color(0xFFD2D2D2)
    val textColor = animateColorAsState(if (selected) selectedColor else unselectedColor, label = "TextColor").value
    val fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(120.dp)
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                painter = icon,
                contentDescription = text,
                modifier = Modifier.size(28.dp),
                tint = textColor
            )
        }
        Text(
            text = text,
            fontSize = 14.sp,
            color = textColor,
            fontWeight = fontWeight,
            textAlign = TextAlign.Center
        )
    }
}
