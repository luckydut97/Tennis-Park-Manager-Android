package com.luckydut97.tennispark_tablet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.window.Dialog
import com.luckydut97.tennispark_tablet.ui.components.BottomNavigationBar
import com.luckydut97.tennispark_tablet.ui.components.TabletTopBar
import com.luckydut97.tennispark_tablet.ui.theme.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.platform.LocalView

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
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    val weekDays = listOf("일", "월", "화", "수", "목", "금", "토")
    val navBarHeight = 110.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TennisGreen)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(bottom = navBarHeight),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TabletTopBar(title = "활동 등록", onBack = onNavigateBack)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()) // 내용이 길때 스크롤 처리
                    .padding(horizontal = 40.dp, vertical = 20.dp),
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
                            onClick = { showStartTimePicker = true },
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
                            onClick = { showEndTimePicker = true },
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
                                    containerColor = if (selectedDays.contains(day)) Color.White else Color(
                                        0xFF0D6042
                                    ),
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
        }
        // 하단 네비게이션 (항상 하단 고정)
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

    // 시작 시간 선택 다이얼로그
    if (showStartTimePicker) {
        TimePickerDialog(
            title = "시작 시간 선택",
            onTimeSelected = { time ->
                startTime = time
                showStartTimePicker = false
            },
            onDismiss = { showStartTimePicker = false }
        )
    }

    // 종료 시간 선택 다이얼로그
    if (showEndTimePicker) {
        TimePickerDialog(
            title = "종료 시간 선택",
            onTimeSelected = { time ->
                endTime = time
                showEndTimePicker = false
            },
            onDismiss = { showEndTimePicker = false }
        )
    }
}

@Composable
private fun TimePickerDialog(
    title: String,
    onTimeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedHour by remember { mutableStateOf(0) }
    var selectedMinute by remember { mutableStateOf(0) }

    val hours = (0..23).toList()
    val minutes = listOf(0, 30)

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .width(350.dp)
                .height(400.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Time Picker Row
                Row(
                    modifier = Modifier.height(200.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Hour Picker
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "시",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        LazyColumn(
                            modifier = Modifier.height(150.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(hours) { hour ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(40.dp)
                                        .clickable { selectedHour = hour }
                                        .background(
                                            if (selectedHour == hour) Color(0xFF0D6042) else Color.Transparent,
                                            RoundedCornerShape(8.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "%02d".format(hour),
                                        fontSize = 18.sp,
                                        fontWeight = if (selectedHour == hour) FontWeight.Bold else FontWeight.Normal,
                                        color = if (selectedHour == hour) Color.White else Color.Black
                                    )
                                }
                            }
                        }
                    }

                    // Separator
                    Text(
                        text = ":",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    // Minute Picker
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "분",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        LazyColumn(
                            modifier = Modifier.height(150.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(minutes) { minute ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(40.dp)
                                        .clickable { selectedMinute = minute }
                                        .background(
                                            if (selectedMinute == minute) Color(0xFF0D6042) else Color.Transparent,
                                            RoundedCornerShape(8.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "%02d".format(minute),
                                        fontSize = 18.sp,
                                        fontWeight = if (selectedMinute == minute) FontWeight.Bold else FontWeight.Normal,
                                        color = if (selectedMinute == minute) Color.White else Color.Black
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Gray,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "취소",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = {
                            val timeString = "%02d:%02d".format(selectedHour, selectedMinute)
                            onTimeSelected(timeString)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0D6042),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "확인",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 1600, heightDp = 2560)
@Composable
fun ActivityRegistrationScreenPreview() {
    Tennispark_tabletTheme {
        ActivityRegistrationScreen(
            selectedItem = "활동 관리",
            onNavigateToHome = {},
            onNavigateToActivity = {},
            onNavigateToEvent = {},
            onNavigateToSettings = {},
            onNavigateBack = {}
        )
    }
}
