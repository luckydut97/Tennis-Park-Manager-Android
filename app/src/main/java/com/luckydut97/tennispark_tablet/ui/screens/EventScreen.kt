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
            TabletBannerSlot("활동신청", "(300X100)"),
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
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // 광고 배너 섹션
            Row(
                modifier = Modifier.width(754.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "광고 배너",
                    color = White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // 광고 배너 컨테이너
            Box(
                modifier = Modifier
                    .width(754.dp)
                    .height(342.76.dp)
                    .background(Color(0xFFF2FAF4), RoundedCornerShape(8.dp))
                    .padding(24.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // 첫 번째 행 - 큰 카드들
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(46.dp)
                    ) {
                        BannerCard(bannerSlots[0], Modifier.size(330.dp, 157.dp))
                        BannerCard(bannerSlots[1], Modifier.size(330.dp, 157.dp))
                    }
                    // 두 번째 행 - 작은 카드들
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(46.dp)
                    ) {
                        BannerCard(bannerSlots[2], Modifier.size(330.dp, 110.dp))
                        BannerCard(bannerSlots[3], Modifier.size(330.dp, 110.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(46.dp))

            // 이벤트 섹션
            Row(
                modifier = Modifier.width(754.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "이벤트",
                    color = White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                eventItems.forEach { event ->
                    EventCard(
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

            // 경기 기록 하기 버튼
            Button(
                onClick = { /* Handle record game */ },
                modifier = Modifier.size(474.dp, 59.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFFF57),
                    contentColor = Color(0xFF145F44)
                ),
                shape = RoundedCornerShape(30.dp)
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

    }
}

@Composable
private fun BannerCard(
    banner: TabletBannerSlot,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        // 배너 배경 박스
        Box(
            modifier = Modifier
                .width(330.dp)
                .height(if (banner.title == "메인화면" || banner.title == "활동신청") 110.dp else 55.dp)
                .background(Color(0xFFD8EBDC), RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 텍스트와 버튼
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${banner.title} ${banner.size}",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            
            OutlinedButton(
                onClick = { /* Handle edit */ },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF08432E)
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF08432E)),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                modifier = Modifier.height(32.dp)
            ) {
                Text(
                    text = "수정",
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun EventCard(
    event: TabletEventItem,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(754.dp)
            .height(78.dp)
            .background(Color(0xFFF2FAF4), RoundedCornerShape(8.dp))
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = event.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "${event.points} Point",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                OutlinedButton(
                    onClick = onClick,
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFF08432E)
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF08432E)),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    modifier = Modifier.height(40.dp)
                ) {
                    Text(
                        text = "수정",
                        fontSize = 16.sp
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
