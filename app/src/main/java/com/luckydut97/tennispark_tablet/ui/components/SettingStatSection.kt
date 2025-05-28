package com.luckydut97.tennispark_tablet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val StatValueColor = Color(0xFF145F44)
val StatLabelColor = Color(0xFF787878)
val StatDividerColor = Color(0xFFD2D2D2)

// 데이터 클래스
 data class StatItem(val value: String, val label: String)

@Composable
fun SettingStatSection(
    title: String,
    period: String,
    items: List<StatItem>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(754.dp)
            .height(165.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = period,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
        }
        Spacer(Modifier.height(11.dp))
        StatCardBox(items = items)
    }
}

@Composable
private fun StatCardBox(
    items: List<StatItem>
) {
    Row(
        Modifier
            .width(754.dp)
            .height(118.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { idx, item ->
            Column(
                Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = item.value,
                    color = StatValueColor,
                    fontSize = 31.65.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = item.label,
                    color = StatLabelColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                )
            }
            // Divider(마지막 반복 제외)
            if (idx < items.size - 1) {
                Box(
                    Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(StatDividerColor)
                )
            }
        }
    }
}
