package com.luckydut97.tennispark_tablet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.tooling.preview.Preview
import com.luckydut97.tennispark_tablet.ui.components.BottomNavigationBar
import com.luckydut97.tennispark_tablet.ui.theme.*

data class TabletBannerSlot(
    val title: String,
    val size: String,
    val isUploaded: Boolean = false
)

data class TabletEventItem(
    val title: String,
    val points: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabletEventScreen(
    selectedItem: String,
    onNavigateToHome: () -> Unit,
    onNavigateToActivity: () -> Unit,
    onNavigateToEvent: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    var showEventDialog by remember { mutableStateOf(false) }

    val bannerSlots = remember {
        listOf(
            TabletBannerSlot("메인화면", "(300X100)"),
            TabletBannerSlot("활동시장", "(300X100)"),
            TabletBannerSlot("구매목록", "(300X50)"),
            TabletBannerSlot("회원정보", "(300X50)")
        )
    }

    val eventItems = remember {
        listOf(
            TabletEventItem("이벤트 만들기", 100),
            TabletEventItem("출석 체크", 100)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TennisGreen)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "이벤트 관리",
                    color = White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = onNavigateToHome,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = TennisGreen
            ),
            modifier = Modifier.height(80.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 40.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Banner Section
            Text(
                text = "광고 배너",
                color = White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(30.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(bannerSlots) { banner ->
                        TabletBannerCard(banner = banner)
                    }
                }
            }

            // Events Section
            Text(
                text = "이벤트",
                color = White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                eventItems.forEach { event ->
                    TabletEventCard(
                        event = event,
                        onClick = {
                            if (event.title == "이벤트 만들기") {
                                showEventDialog = true
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Bottom Button
            Button(
                onClick = { /* Handle record game */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(59.dp)
                    .padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = TennisYellow,
                    contentColor = TennisGreen
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "경기 기록 하기",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
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

    // Event Creation Dialog
    if (showEventDialog) {
        TabletEventCreationDialog(
            onDismiss = { showEventDialog = false },
            onConfirm = {
                showEventDialog = false
                // Handle event creation
            }
        )
    }
}

@Composable
private fun StatusBarArea() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "9:41 AM",
            color = White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "Tue Sep 24",
            color = White,
            fontSize = 18.sp
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(White.copy(0.8f), RoundedCornerShape(2.dp))
            )
            Text(
                text = "100%",
                color = White,
                fontSize = 18.sp
            )
            Box(
                modifier = Modifier
                    .width(28.dp)
                    .height(16.dp)
                    .background(White.copy(0.8f), RoundedCornerShape(2.dp))
            )
        }
    }
}

@Composable
private fun TabletBannerCard(banner: TabletBannerSlot) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (banner.isUploaded) TennisLightGreen else TennisVeryLightGreen
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Banner preview area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        Color.LightGray.copy(alpha = 0.3f),
                        RoundedCornerShape(12.dp)
                    )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Banner info
            Text(
                text = banner.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TennisGreen
            )
            Text(
                text = banner.size,
                fontSize = 14.sp,
                color = Gray
            )

            // Upload button
            OutlinedButton(
                onClick = { /* Handle upload */ },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = TennisGreen
                ),
                border = androidx.compose.foundation.BorderStroke(2.dp, TennisGreen),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                modifier = Modifier.height(40.dp)
            ) {
                Text(
                    text = "수정",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun TabletEventCard(
    event: TabletEventItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = event.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TennisGreen
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "${event.points} Point",
                    fontSize = 16.sp,
                    color = Gray
                )

                OutlinedButton(
                    onClick = onClick,
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = TennisGreen
                    ),
                    border = androidx.compose.foundation.BorderStroke(2.dp, TennisGreen),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                    modifier = Modifier.height(48.dp)
                ) {
                    Text(
                        text = "수정",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun TabletEventCreationDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var eventName by remember { mutableStateOf("") }
    var eventContent by remember { mutableStateOf("") }
    var eventPoints by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(40.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(40.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                // Header with backdrop info
                Column {
                    Text(
                        text = "버튼시트 백그운드",
                        fontSize = 16.sp,
                        color = Gray,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "#000000 60%로 모두 동일하게 처리 부탁드립니다!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TennisGreen
                    )
                }

                Text(
                    text = "새로운 이벤트 생성",
                    fontSize = 20.sp,
                    color = Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "이벤트 등록",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TennisGreen,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                // Event Name
                Text(
                    text = "이벤트명",
                    fontSize = 18.sp,
                    color = TennisGreen,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = eventName,
                    onValueChange = { eventName = it },
                    placeholder = {
                        Text(
                            text = "이벤트 명을 입력해주세요",
                            color = Gray,
                            fontSize = 16.sp
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = TennisGreen,
                        unfocusedBorderColor = TennisLightGreen,
                        focusedContainerColor = TennisVeryLightGreen,
                        unfocusedContainerColor = TennisVeryLightGreen
                    ),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
                )

                // Event Content
                Text(
                    text = "이벤트 내용",
                    fontSize = 18.sp,
                    color = TennisGreen,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = eventContent,
                    onValueChange = { eventContent = it },
                    placeholder = {
                        Text(
                            text = "이벤트 내용을 입력해주세요",
                            color = Gray,
                            fontSize = 16.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = TennisGreen,
                        unfocusedBorderColor = TennisLightGreen,
                        focusedContainerColor = TennisVeryLightGreen,
                        unfocusedContainerColor = TennisVeryLightGreen
                    ),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
                    maxLines = 4
                )

                // Event Points
                Text(
                    text = "지급 포인트",
                    fontSize = 18.sp,
                    color = TennisGreen,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = eventPoints,
                    onValueChange = { eventPoints = it },
                    placeholder = {
                        Text(
                            text = "지급 포인트를 입력해주세요",
                            color = Gray,
                            fontSize = 16.sp
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = TennisGreen,
                        unfocusedBorderColor = TennisLightGreen,
                        focusedContainerColor = TennisVeryLightGreen,
                        unfocusedContainerColor = TennisVeryLightGreen
                    ),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
                )

                // Confirm Button
                Button(
                    onClick = onConfirm,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TennisGreen,
                        contentColor = White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "등록하기",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 1600, heightDp = 2560)
@Composable
fun TabletEventScreenPreview() {
    Tennispark_tabletTheme {
        TabletEventScreen(
            selectedItem = "이벤트 관리",
            onNavigateToHome = {},
            onNavigateToActivity = {},
            onNavigateToEvent = {},
            onNavigateToSettings = {}
        )
    }
}
