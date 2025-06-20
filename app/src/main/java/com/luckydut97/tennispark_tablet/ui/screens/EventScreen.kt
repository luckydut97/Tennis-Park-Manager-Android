package com.luckydut97.tennispark_tablet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.luckydut97.tennispark_tablet.ui.components.BottomNavigationBar
import com.luckydut97.tennispark_tablet.ui.components.TabletTopBar
import com.luckydut97.tennispark_tablet.ui.theme.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.saveable.rememberSaveable
import com.luckydut97.tennispark_tablet.ui.components.EventCreateBottomSheet
import com.luckydut97.tennispark_tablet.ui.components.EventEditBottomSheet
import com.luckydut97.tennispark_tablet.ui.components.GameRecordBottomSheet
import com.luckydut97.tennispark_tablet.data.repository.EventRepository
import com.luckydut97.tennispark_tablet.data.network.event.EventItem
import com.luckydut97.tennispark_tablet.ui.viewmodel.EventViewModel
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.launch
import com.luckydut97.tennispark_tablet.data.network.ApiProvider
import com.luckydut97.tennispark_tablet.ui.screens.BannerManageType
import com.luckydut97.tennispark_tablet.ui.screens.BannerManageScreen
import android.widget.Toast
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.tooling.preview.Preview

data class TabletBannerSlot(
    val title: String,
    val size: String,
    val isUploaded: Boolean = false
)

data class TabletEventItem(
    val title: String,
    val content: String,
    val points: Int
)

@Composable
fun TabletEventScreen(
    selectedItem: String,
    onNavigateToHome: () -> Unit,
    onNavigateToActivity: () -> Unit,
    onNavigateToEvent: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToBannerManage: (BannerManageType) -> Unit = {}
) {
    val eventRepository = remember { EventRepository(ApiProvider.eventService) }
    val eventViewModel = remember { EventViewModel(eventRepository) }
    val events by eventViewModel.eventList.collectAsState()
    val isLoading by eventViewModel.isLoading.collectAsState()
    val errorMessage by eventViewModel.errorMessage.collectAsState()
    val context = LocalContext.current

    var showEventDialog by remember { mutableStateOf(false) }
    var showEditDialog by rememberSaveable { mutableStateOf(false) }
    var showGameDialog by remember { mutableStateOf(false) }
    var selectedEvent by rememberSaveable { mutableStateOf<EventItem?>(null) }

    // 배너 관리 화면 상태 추가
    var currentBannerManageType by remember { mutableStateOf<BannerManageType?>(null) }

    val bannerSlots = remember {
        listOf(
            TabletBannerSlot("메인화면", "(300X100)"),
            TabletBannerSlot("활동신청", "(300X100)"),
            TabletBannerSlot("구매목록", "(300X100)"),
            TabletBannerSlot("회원정보", "(300X100)")
        )
    }

    val navBarHeight = 110.dp

    LaunchedEffect(Unit) {
        eventViewModel.fetchEvents()
    }

    // 배너 관리 화면이 활성화된 경우
    if (currentBannerManageType != null) {
        BannerManageScreen(
            manageType = currentBannerManageType!!,
            onBack = {
                currentBannerManageType = null
            }
        )
        return
    }

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
            verticalArrangement = Arrangement.Top
        ) {
            TabletTopBar(title = "이벤트 관리", onBack = onNavigateToHome)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 40.dp)
                    .padding(top = 20.dp), // 상단 패딩만 추가
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // 광고 배너 섹션
                Row(
                    modifier = Modifier.fillMaxWidth(),
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
                        .fillMaxWidth()
                        .background(Color(0xFFF2FAF4), RoundedCornerShape(8.dp))
                        .padding(24.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        // 첫 번째 행 - 큰 카드들
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(46.dp)
                        ) {
                            BannerCard(
                                banner = bannerSlots[0],
                                modifier = Modifier
                                    .weight(1f)
                                    .height(157.dp),
                                onEditClick = { currentBannerManageType = BannerManageType.MAIN }
                            )
                            BannerCard(
                                banner = bannerSlots[1],
                                modifier = Modifier
                                    .weight(1f)
                                    .height(157.dp),
                                onEditClick = {
                                    currentBannerManageType = BannerManageType.ACTIVITY
                                }
                            )
                        }
                        // 두 번째 행 - 작은 카드들
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(46.dp)
                        ) {
                            BannerCard(
                                banner = bannerSlots[2],
                                modifier = Modifier
                                    .weight(1f)
                                    .height(157.dp),
                                onEditClick = {
                                    currentBannerManageType = BannerManageType.PURCHASE
                                }
                            )
                            BannerCard(
                                banner = bannerSlots[3],
                                modifier = Modifier
                                    .weight(1f)
                                    .height(157.dp),
                                onEditClick = {
                                    currentBannerManageType = BannerManageType.MEMBER
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // 이벤트 섹션
                Row(
                    modifier = Modifier.fillMaxWidth(),
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
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    events.forEach { event ->
                        EventCard(
                            event = TabletEventItem(
                                event.title,
                                event.content,
                                event.point
                            ), // 혹은 필요하면 확장
                            onClick = {
                                selectedEvent = event
                                showEditDialog = true
                            }
                        )
                    }

                    // 이벤트 추가 버튼
                    Button(
                        onClick = { showEventDialog = true },
                        modifier = Modifier
                            .size(160.dp, 40.dp)
                            .align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF145F44),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "+ 이벤트 추가",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                }

                // 경기 기록 하기 & 이번주 활동 사진 버튼들
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Button(
                        onClick = { showGameDialog = true },
                        modifier = Modifier
                            .weight(1f)
                            .height(59.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFFF57),
                            contentColor = Color(0xFF08432E)
                        ),
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Text(
                            text = "경기 기록 하기",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = { /* Handle weekly photos */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(59.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFFF57),
                            contentColor = Color(0xFF08432E)
                        ),
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Text(
                            text = "이번주 활동 사진",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
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

    // Event Creation Dialog
    if (showEventDialog) {
        EventCreateBottomSheet(
            onDismiss = { showEventDialog = false },
            onConfirm = { name, content, points ->
                eventViewModel.createEvent(name, content, points)
                if (errorMessage != null) {
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "이벤트가 등록되었습니다", Toast.LENGTH_SHORT).show()
                }
                showEventDialog = false
            }
        )
    }

    // Event Edit Dialog
    if (showEditDialog && selectedEvent != null) {
        EventEditBottomSheet(
            event = TabletEventItem(
                selectedEvent!!.title,
                selectedEvent!!.content,
                selectedEvent!!.point
            ),
            onDismiss = { showEditDialog = false },
            onDelete = {
                eventViewModel.deleteEvent(selectedEvent!!.id)
                showEditDialog = false
            },
            onUpdate = { name, content, points ->
                eventViewModel.updateEvent(selectedEvent!!.id, name, content, points)
                showEditDialog = false
            }
        )
    }

    // Game Record Dialog
    if (showGameDialog) {
        GameRecordBottomSheet(
            onDismiss = { showGameDialog = false },
            onConfirm = { teamA1, teamA2, teamB1, teamB2, scoreA, scoreB ->
                // Handle game record
                showGameDialog = false
            }
        )
    }
}

@Composable
private fun BannerCard(
    banner: TabletBannerSlot,
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        // 배너 배경 박스
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
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
                onClick = onEditClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF08432E)
                ),
                border = BorderStroke(1.dp, Color(0xFF08432E)),
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
            .fillMaxWidth()
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
                    border = BorderStroke(1.dp, Color(0xFF08432E)),
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
