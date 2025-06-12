package com.luckydut97.tennispark_tablet.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
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
    // 위쪽 그림자를 위한 Box 추가
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // 위쪽 그림자 효과
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(
                    androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.1f),
                            Color.Transparent
                        )
                    )
                )
        )

        // 실제 네비게이션바
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
            color = Color.White
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp), // 12dp에서 8dp로 줄임
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BottomNavItem(
                    icon = if (selectedItem == "홈") painterResource(R.drawable.ic_home_active) else painterResource(
                        R.drawable.ic_home_deactive
                    ),
                    text = "홈",
                    selected = selectedItem == "홈",
                    onClick = onNavigateToHome
                )
                BottomNavItem(
                    icon = if (selectedItem == "활동 관리") painterResource(R.drawable.activity_active) else painterResource(
                        R.drawable.activity_deactive
                    ),
                    text = "활동 관리",
                    selected = selectedItem == "활동 관리",
                    onClick = onNavigateToActivity
                )
                BottomNavItem(
                    icon = if (selectedItem == "이벤트 관리") painterResource(R.drawable.event_active) else painterResource(
                        R.drawable.event_deactive
                    ),
                    text = "이벤트 관리",
                    selected = selectedItem == "이벤트 관리",
                    onClick = onNavigateToEvent
                )
                BottomNavItem(
                    icon = if (selectedItem == "설정") painterResource(R.drawable.ic_setting_active) else painterResource(
                        R.drawable.ic_setting_deactive
                    ),
                    text = "설정",
                    selected = selectedItem == "설정",
                    onClick = onNavigateToSettings
                )
            }
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
        modifier = Modifier
            .width(120.dp)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp) // 클릭 영역 보완
    ) {
        Icon(
            painter = icon,
            contentDescription = text,
            modifier = Modifier.size(32.dp), // 28dp에서 32dp로 증가
            tint = textColor
        )
        Text(
            text = text,
            fontSize = 14.sp,
            color = textColor,
            fontWeight = fontWeight,
            textAlign = TextAlign.Center
        )
    }
}
