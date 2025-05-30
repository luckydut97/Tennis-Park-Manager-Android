package com.luckydut97.tennispark_tablet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckydut97.tennispark_tablet.ui.components.BottomNavigationBar
import com.luckydut97.tennispark_tablet.ui.components.TabletTopBar
import com.luckydut97.tennispark_tablet.ui.theme.*

@Composable
fun ActivityRegistrationScreen(
    selectedItem: String,
    onNavigateToHome: () -> Unit,
    onNavigateToActivity: () -> Unit,
    onNavigateToEvent: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateBack: () -> Unit
) {
    var startTime by remember { mutableStateOf("00:00") }
    var endTime by remember { mutableStateOf("00:00") }
    var selectedDays by remember { mutableStateOf(setOf<String>()) }
    var placeName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var isRepeating by remember { mutableStateOf(true) }

    val weekDays = listOf("일", "월", "화", "수", "목", "금", "토")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TennisGreen),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabletTopBar(title = "활동 등록", onBack = onNavigateBack)

        Column(
            modifier = Modifier
                .weight(1f)
                .width(754.dp)
                .padding(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // 활동 시간 선택 섹션
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row {
                    Text(
                        text = "활동 시간 선택",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "(활동 시작시간 ~ 종료시간)",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "*",
                        fontSize = 18.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Normal
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { /* Handle start time selection */ },
                        modifier = Modifier.size(358.dp, 60.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.Gray),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = startTime,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(38.dp)
                            .height(60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "~",
                            fontSize = 20.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    OutlinedButton(
                        onClick = { /* Handle end time selection */ },
                        modifier = Modifier.size(358.dp, 60.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.Gray),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = endTime,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }

            // 활동 요일 선택 섹션
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row {
                    Text(
                        text = "활동 요일 선택",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "*",
                        fontSize = 18.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Normal
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    weekDays.forEach { day ->
                        Button(
                            onClick = {
                                selectedDays = if (selectedDays.contains(day)) {
                                    selectedDays - day
                                } else {
                                    selectedDays + day
                                }
                            },
                            modifier = Modifier.size(97.dp, 60.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedDays.contains(day)) Color.White else Color(0xFF0D6042),
                                contentColor = if (selectedDays.contains(day)) Color(0xFF0D6042) else Color.White
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = day,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // 운동 장소 섹션
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row {
                    Text(
                        text = "운동 장소",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "*",
                        fontSize = 18.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Normal
                    )
                }

                Text(
                    text = "장소명",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Normal
                )

                OutlinedTextField(
                    value = placeName,
                    onValueChange = { placeName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp),
                    placeholder = {
                        Text(
                            text = "장소를 입력하세요",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                )

                Text(
                    text = "주소",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Normal
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp),
                    placeholder = {
                        Text(
                            text = "주소를 입력하세요",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                )
            }

            // 일정 반복여부 섹션
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row {
                    Text(
                        text = "일정 반복여부",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "*",
                        fontSize = 18.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Normal
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { isRepeating = true },
                        modifier = Modifier.size(371.dp, 60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isRepeating) Color.White else Color(0xFF0D6042),
                            contentColor = if (isRepeating) Color(0xFF0D6042) else Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "반복",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = { isRepeating = false },
                        modifier = Modifier.size(371.dp, 60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!isRepeating) Color.White else Color(0xFF0D6042),
                            contentColor = if (!isRepeating) Color(0xFF0D6042) else Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "반복 안함",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

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
fun ActivityRegistrationScreenPreview() {
    Tennispark_tabletTheme {
        ActivityRegistrationScreen(
            selectedItem = "활동관리",
            onNavigateToHome = {},
            onNavigateToActivity = {},
            onNavigateToEvent = {},
            onNavigateToSettings = {},
            onNavigateBack = {}
        )
    }
}