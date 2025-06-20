package com.luckydut97.tennispark_tablet.ui.screens

import android.widget.Toast
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
import com.luckydut97.tennispark_tablet.data.model.Activity
import com.luckydut97.tennispark_tablet.data.network.ApiProvider
import com.luckydut97.tennispark_tablet.data.repository.ActivityRepository
import com.luckydut97.tennispark_tablet.ui.components.BottomNavigationBar
import com.luckydut97.tennispark_tablet.ui.components.TabletTopBar
import com.luckydut97.tennispark_tablet.ui.screens.ActivityRegistrationScreen
import com.luckydut97.tennispark_tablet.ui.screens.ApplicationListScreen
import com.luckydut97.tennispark_tablet.ui.theme.*
import com.luckydut97.tennispark_tablet.ui.viewmodel.ActivityViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

// 시간 문자열을 HH:mm 형식으로 잘라주는 확장함수(임시)
fun String.formatToHHmm(): String = if (this.length >= 5) this.substring(0, 5) else this

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabletActivityScreen(
    selectedItem: String,
    onNavigateToHome: () -> Unit,
    onNavigateToActivity: () -> Unit,
    onNavigateToEvent: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    // ViewModel을 공통으로 생성(이 인스턴스 하나를 하위 등록화면에도 넘김)
    val activityViewModel =
        remember { ActivityViewModel(ActivityRepository(ApiProvider.activityService)) }
    val activityList: List<Activity> =
        activityViewModel.activityList.collectAsState(initial = emptyList()).value
    LaunchedEffect(Unit) { activityViewModel.refreshActivities() }

    var currentTab by remember { mutableStateOf("활동 등록") }
    var showRegistrationScreen by remember { mutableStateOf(false) }
    var showApplicationListScreen by remember { mutableStateOf(false) }
    var editingActivity by remember { mutableStateOf<Activity?>(null) }

    // 네비게이션바 높이 고려
    val navBarHeight = 110.dp

    if (showRegistrationScreen) {
        ActivityRegistrationScreen(
            selectedItem = selectedItem,
            onNavigateToHome = onNavigateToHome,
            onNavigateToActivity = onNavigateToActivity,
            onNavigateToEvent = onNavigateToEvent,
            onNavigateToSettings = onNavigateToSettings,
            onNavigateBack = {
                showRegistrationScreen = false
                editingActivity = null
            },
            activityViewModel = activityViewModel,
            activityToEdit = editingActivity
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White) // 전체 배경을 흰색으로
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(TennisGreen)
                    .statusBarsPadding()
                    .padding(bottom = navBarHeight), // 네비게이션바 공간 확보
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TabletTopBar(title = "활동 관리", onBack = onNavigateToHome)

                // Tab Row
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp, vertical = 20.dp)
                        .height(67.dp)
                        .background(
                            color = Color(0xFF0D6042),
                            shape = RoundedCornerShape(300.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .padding(4.dp)
                                .clickable { currentTab = "활동 등록" },
                            contentAlignment = Alignment.Center
                        ) {
                            if (currentTab == "활동 등록") {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(300.dp)
                                        )
                                )
                            }
                            Text(
                                text = "활동 등록",
                                fontSize = 24.sp,
                                fontWeight = if (currentTab == "활동 등록") FontWeight.Bold else FontWeight.SemiBold,
                                color = if (currentTab == "활동 등록") Color(0xFF0D6042) else Color(
                                    0xFF359170
                                )
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .padding(4.dp)
                                .clickable { currentTab = "신청 내용" },
                            contentAlignment = Alignment.Center
                        ) {
                            if (currentTab == "신청 내용") {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(300.dp)
                                        )
                                )
                            }
                            Text(
                                text = "신청 내용",
                                fontSize = 24.sp,
                                fontWeight = if (currentTab == "신청 내용") FontWeight.Bold else FontWeight.SemiBold,
                                color = if (currentTab == "신청 내용") Color(0xFF0D6042) else Color(
                                    0xFF359170
                                )
                            )
                        }
                    }
                }

                // Content Area
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (currentTab == "활동 등록") {
                        TabletActivityListContent(
                            activities = activityList,
                            onAddClick = { showRegistrationScreen = true },
                            onEditClick = { activity ->
                                editingActivity = activity
                                showRegistrationScreen = true
                            },
                            activityViewModel = activityViewModel
                        )
                    } else {
                        TabletApplicationContent {
                            showApplicationListScreen = true
                        }
                    }
                }
            }

            // 하단 네비게이션 - 절대 위치로 하단에 고정
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
    }
}

@Composable
private fun TabletActivityListContent(
    activities: List<Activity>,
    onAddClick: () -> Unit,
    onEditClick: (Activity) -> Unit,
    activityViewModel: ActivityViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(activities) { activity ->
                TabletActivityCard(
                    activity = activity,
                    onEditClick = { onEditClick(activity) },
                    activityViewModel = activityViewModel
                )
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
                .padding(bottom = 60.dp)
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
private fun TabletActivityCard(
    activity: Activity,
    onEditClick: () -> Unit,
    activityViewModel: ActivityViewModel
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2FAF4)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp), // 모든 간격을 16dp로 통일
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 왼쪽: 장소명 + 코트명 (각각 고정 구역, 다른 정렬)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp), // 활동명-코트명 간격 16dp
                modifier = Modifier
                    .weight(1f, fill = false) // 필요한 만큼만 공간 사용
                    .widthIn(min = 200.dp, max = 400.dp) // 최소/최대 너비 제한
            ) {
                // 활동명 구역 - 좌측 정렬
                Box(
                    modifier = Modifier
                        .weight(2f) // 활동명이 코트명보다 더 많은 공간 할당
                        .widthIn(min = 120.dp),
                    contentAlignment = Alignment.CenterStart // 좌측 정렬
                ) {
                    Text(
                        text = activity.location,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start // 좌측 정렬
                    )
                }

                // 코트명 구역 - 가운데 정렬
                Box(
                    modifier = Modifier
                        .weight(1f) // 코트명은 상대적으로 적은 공간
                        .widthIn(min = 80.dp),
                    contentAlignment = Alignment.Center // 가운데 정렬
                ) {
                    Text(
                        text = activity.court,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center // 가운데 정렬
                    )
                }
            }

            // 시간 표시 (단독 구역)
            Text(
                text = "${activity.startTime} ~ ${activity.endTime}",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 1,
                modifier = Modifier.widthIn(min = 145.dp) // 시간 표시 최소 너비 확보
            )

            // 버튼 구역 (수정, 삭제)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp) // 버튼 간 간격은 8dp 유지
            ) {
                OutlinedButton(
                    onClick = onEditClick,
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

                OutlinedButton(
                    onClick = {
                        scope.launch {
                            val result = activityViewModel.deleteActivity(activity.id)
                            if (result) {
                                Toast.makeText(context, "활동이 삭제되었습니다", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(context, "활동 삭제에 실패했습니다", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    },
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
                        text = "삭제",
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
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "등록된 활동의 신청 내용이 표시됩니다.",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(vertical = 20.dp)
            )
        }
        item {
            Spacer(modifier = Modifier.height(40.dp)) // Bottom spacing for navigation bar
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
