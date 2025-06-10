package com.luckydut97.tennispark_tablet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckydut97.tennispark_tablet.ui.components.BottomNavigationBar
import com.luckydut97.tennispark_tablet.ui.components.TabletTopBar
import com.luckydut97.tennispark_tablet.ui.screens.ActivityRegistrationScreen
import com.luckydut97.tennispark_tablet.ui.screens.ApplicationListScreen
import com.luckydut97.tennispark_tablet.ui.theme.*

data class TabletActivity(
    val location: String,
    val court: String,
    val time: String,
    val currentParticipants: Int = 0,
    val maxParticipants: Int = 12
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabletActivityScreen(
    selectedItem: String,
    onNavigateToHome: () -> Unit,
    onNavigateToActivity: () -> Unit,
    onNavigateToEvent: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    var currentTab by remember { mutableStateOf("활동 등록") }
    var showRegistrationScreen by remember { mutableStateOf(false) }
    var showApplicationListScreen by remember { mutableStateOf(false) }

    val activities = remember {
        listOf(
            TabletActivity("수도공고 테니스장", "B코트", "10:00 - 12:00", 8, 12),
            TabletActivity("강남 테니스장", "C코트", "14:00 - 16:00", 12, 12),
            TabletActivity("서초 테니스장", "D코트", "18:00 - 20:00", 3, 12)
        )
    }

    if (showRegistrationScreen) {
        ActivityRegistrationScreen(
            selectedItem = selectedItem,
            onNavigateToHome = onNavigateToHome,
            onNavigateToActivity = onNavigateToActivity,
            onNavigateToEvent = onNavigateToEvent,
            onNavigateToSettings = onNavigateToSettings,
            onNavigateBack = { showRegistrationScreen = false }
        )
    } else if (showApplicationListScreen) {
        ApplicationListScreen(
            selectedItem = selectedItem,
            onNavigateToHome = onNavigateToHome,
            onNavigateToActivity = onNavigateToActivity,
            onNavigateToEvent = onNavigateToEvent,
            onNavigateToSettings = onNavigateToSettings,
            onNavigateBack = { showApplicationListScreen = false }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(TennisGreen),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TabletTopBar(title = "활동 관리", onBack = onNavigateToHome)

            // Tab Row
            Row(
                modifier = Modifier
                    .width(755.dp)
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TabletTabButton(
                    text = "활동 등록",
                    selected = currentTab == "활동 등록",
                    onClick = { currentTab = "활동 등록" },
                    modifier = Modifier.weight(1f)
                )
                TabletTabButton(
                    text = "신청 내용",
                    selected = currentTab == "신청 내용",
                    onClick = { currentTab = "신청 내용" },
                    modifier = Modifier.weight(1f)
                )
            }

            // Content Area
            Box(
                modifier = Modifier.weight(1f)
            ) {
                if (currentTab == "활동 등록") {
                    TabletActivityListContent(activities = activities) {
                        showRegistrationScreen = true
                    }
                } else {
                    TabletApplicationContent {
                        showApplicationListScreen = true
                    }
                }
            }

            // Bottom Navigation
            BottomNavigationBar(
                selectedItem = selectedItem,
                onNavigateToHome = onNavigateToHome,
                onNavigateToActivity = onNavigateToActivity,
                onNavigateToEvent = onNavigateToEvent,
                onNavigateToSettings = onNavigateToSettings
            )
        }
    }
}

@Composable
private fun TabletTabButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(60.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) White else Color.Transparent,
            contentColor = if (selected) TennisGreen else White
        ),
        shape = RoundedCornerShape(30.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 2.dp,
            color = if (selected) White else White.copy(alpha = 0.5f)
        )
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun TabletActivityListContent(activities: List<TabletActivity>, onAddClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(activities) { activity ->
                TabletActivityCard(activity = activity)
            }
            item {
                Spacer(modifier = Modifier.height(100.dp)) // Add space for FAB
            }
        }

        // Add Button
        FloatingActionButton(
            onClick = onAddClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .size(80.dp),
            containerColor = White,
            contentColor = TennisGreen
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
private fun TabletActivityCard(activity: TabletActivity) {
    Card(
        modifier = Modifier
            .width(755.dp)
            .height(78.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = activity.location,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = activity.court,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = activity.time,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                OutlinedButton(
                    onClick = { /* Handle edit */ },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFF08432E)
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF08432E)),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier.size(102.dp, 46.dp)
                ) {
                    Text(
                        text = "수정",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun TabletApplicationContent(onCardClick: () -> Unit) {
    val applicationActivities = remember {
        listOf(
            TabletActivity("수도공고 테니스장", "B코트", "10:00 - 12:00", 11, 12),
            TabletActivity("수도공고 테니스장", "C코트", "14:00 - 16:00", 0, 12)
        )
    }
    
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(applicationActivities) { activity ->
            TabletApplicationCard(
                activity = activity,
                onClick = onCardClick
            )
        }
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
private fun TabletActivityFormCard() {
    var startTime by remember { mutableStateOf("12:00") }
    var endTime by remember { mutableStateOf("14:00") }
    var selectedDays by remember { mutableStateOf(setOf("토")) }
    var location by remember { mutableStateOf("수도권 테니스장") }
    var address by remember { mutableStateOf("서울 강남구 개포로 410 수도전기공고등학교") }
    var isRepeating by remember { mutableStateOf(true) }
    var maxParticipants by remember { mutableStateOf(12) }

    val weekDays = listOf("일", "월", "화", "수", "목", "금", "토")

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(40.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Time Selection
            Text(
                text = "활동 시간 선택 (활동 시작시간 ~ 종료시간)",
                fontSize = 18.sp,
                color = TennisGreen,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = startTime,
                    onValueChange = { startTime = it },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = TennisGreen,
                        unfocusedBorderColor = TennisLightGreen,
                        focusedContainerColor = TennisVeryLightGreen,
                        unfocusedContainerColor = TennisVeryLightGreen
                    ),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
                )

                Text(
                    text = "~",
                    fontSize = 24.sp,
                    color = TennisGreen,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = endTime,
                    onValueChange = { endTime = it },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = TennisGreen,
                        unfocusedBorderColor = TennisLightGreen,
                        focusedContainerColor = TennisVeryLightGreen,
                        unfocusedContainerColor = TennisVeryLightGreen
                    ),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
                )
            }

            // Day Selection
            Text(
                text = "활동 요일 선택",
                fontSize = 18.sp,
                color = TennisGreen,
                fontWeight = FontWeight.Bold
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(weekDays) { day ->
                    TabletDayButton(
                        day = day,
                        selected = selectedDays.contains(day),
                        onClick = {
                            selectedDays = if (selectedDays.contains(day)) {
                                selectedDays - day
                            } else {
                                selectedDays + day
                            }
                        }
                    )
                }
            }

            // Location
            Text(
                text = "운동 장소",
                fontSize = 18.sp,
                color = TennisGreen,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = TennisGreen,
                    unfocusedBorderColor = TennisLightGreen,
                    focusedContainerColor = TennisVeryLightGreen,
                    unfocusedContainerColor = TennisVeryLightGreen
                ),
                shape = RoundedCornerShape(12.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
            )

            // Address
            Text(
                text = "주소",
                fontSize = 18.sp,
                color = TennisGreen,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = TennisGreen,
                    unfocusedBorderColor = TennisLightGreen,
                    focusedContainerColor = TennisVeryLightGreen,
                    unfocusedContainerColor = TennisVeryLightGreen
                ),
                shape = RoundedCornerShape(12.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
            )

            // Max Participants
            Text(
                text = "최대 인원",
                fontSize = 18.sp,
                color = TennisGreen,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = maxParticipants.toString(),
                onValueChange = {
                    if (it.isNotBlank()) {
                        maxParticipants = it.toIntOrNull() ?: 12
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = TennisGreen,
                    unfocusedBorderColor = TennisLightGreen,
                    focusedContainerColor = TennisVeryLightGreen,
                    unfocusedContainerColor = TennisVeryLightGreen
                ),
                shape = RoundedCornerShape(12.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
            )

            // Repeat Options
            Text(
                text = "일정 반복여부",
                fontSize = 18.sp,
                color = TennisGreen,
                fontWeight = FontWeight.Bold
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TabletRepeatButton(
                    text = "반복",
                    selected = isRepeating,
                    onClick = { isRepeating = true },
                    modifier = Modifier.weight(1f)
                )
                TabletRepeatButton(
                    text = "반복 안 함",
                    selected = !isRepeating,
                    onClick = { isRepeating = false },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun TabletDayButton(
    day: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (selected) White else Color.Transparent
            )
            .border(
                2.dp,
                if (selected) TennisGreen else TennisLightGreen,
                RoundedCornerShape(12.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day,
            fontSize = 18.sp,
            color = if (selected) TennisGreen else White,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun TabletRepeatButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(60.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) White else Color.Transparent,
            contentColor = if (selected) TennisGreen else TennisGreen
        ),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(2.dp, TennisGreen)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun TabletApplicationCard(activity: TabletActivity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(755.dp)
            .height(78.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = activity.location,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = activity.court,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = activity.time,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Box(
                    modifier = Modifier
                        .width(102.dp)
                        .height(46.dp)
                        .background(
                            if (activity.maxParticipants - activity.currentParticipants == 1) 
                                Color.Red 
                            else 
                                Color(0xFF08432E),
                            RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${activity.currentParticipants}/${activity.maxParticipants}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 1600, heightDp = 2560)
@Composable
fun TabletActivityScreenPreview() {
    Tennispark_tabletTheme {
        TabletActivityScreen(
            selectedItem = "활동 관리",
            onNavigateToHome = {},
            onNavigateToActivity = {},
            onNavigateToEvent = {},
            onNavigateToSettings = {}
        )
    }
}
