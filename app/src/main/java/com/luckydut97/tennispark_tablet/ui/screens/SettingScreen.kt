package com.luckydut97.tennispark_tablet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckydut97.tennispark_tablet.ui.components.BottomNavigationBar
import com.luckydut97.tennispark_tablet.ui.components.MemberInfo
import com.luckydut97.tennispark_tablet.ui.components.SettingStatSection
import com.luckydut97.tennispark_tablet.ui.components.StatItem
import com.luckydut97.tennispark_tablet.ui.components.MemberInfoSection
import com.luckydut97.tennispark_tablet.ui.components.TabletTopBar
import com.luckydut97.tennispark_tablet.ui.theme.TennisGreen
import com.luckydut97.tennispark_tablet.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToActivity: () -> Unit,
    onNavigateToEvent: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    var showPushDialog by remember { mutableStateOf(false) }
    var memberList by remember { mutableStateOf(dummyMemberData()) }
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(TennisGreen),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TabletTopBar(title = "설정", onBack = onNavigateToHome)
            Spacer(modifier = Modifier.height(70.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                SettingStatSection(
                    title = "이번달 회원 활동",
                    period = "(2025.04.01 ~ 04.30)",
                    items = listOf(
                        StatItem("120명", "참여 인원 수"),
                        StatItem("120명", "누적 포인트"),
                        StatItem("120명", "최다 포인트 적립자"),
                        StatItem("120명", "최다 포인트 사용자")
                    )
                )
                Spacer(modifier = Modifier.height(70.dp))

                SettingStatSection(
                    title = "회원통계",
                    period = "(2025.04.01 ~ 04.30)",
                    items = listOf(
                        StatItem("500명", "총 회원 수"),
                        StatItem("800회", "총 활동 횟수"),
                        StatItem("홍길동", "리그 1위 회원"),
                        StatItem("95점", "리그 1위 점수")
                    )
                )
                Spacer(modifier = Modifier.height(70.dp))

                MemberInfoSection(
                    memberList = memberList,
                    onSearch = { query ->
                        // 검색 로직 - 현재는 더미 데이터로 필터링
                        memberList = if (query.isEmpty()) {
                            dummyMemberData()
                        } else {
                            dummyMemberData().filter {
                                it.id.contains(query, ignoreCase = true) ||
                                        it.instaId.contains(query, ignoreCase = true)
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(70.dp))

                // 앱 푸시 버튼
                Button(
                    onClick = { showPushDialog = true },
                    modifier = Modifier
                        .width(754.dp)
                        .height(59.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFFF57),
                        contentColor = Color(0xFF145F44)
                    ),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Text(
                        text = "앱 푸시",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(100.dp)) // Bottom Navigation 공간
            }

            BottomNavigationBar(
                selectedItem = "설정",
                onNavigateToHome = onNavigateToHome,
                onNavigateToActivity = onNavigateToActivity,
                onNavigateToEvent = onNavigateToEvent,
                onNavigateToSettings = onNavigateToSettings
            )
        }

        // 앱 푸시 다이얼로그
        if (showPushDialog) {
            AppPushDialog(
                onDismiss = { showPushDialog = false },
                onConfirm = { message ->
                    // 푸시 전송 로직 처리
                    showPushDialog = false
                }
            )
        }
    }
}

@Composable
private fun AppPushDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var pushMessage by remember { mutableStateOf("") }

    // 바텀 시트 스타일 다이얼로그
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .systemBarsPadding(),
        contentAlignment = Alignment.BottomCenter
    ) {
        // 배경 클릭 시 닫기
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onDismiss() }
        )

        // 바텀 시트 컨텐츠
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // 제목
                Text(
                    text = "앱푸시",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )

                // 부제목
                Text(
                    text = "공지사항 안내",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF555555)
                )

                // 메시지 입력 필드
                OutlinedTextField(
                    value = pushMessage,
                    onValueChange = { pushMessage = it },
                    placeholder = {
                        Text(
                            text = "회원들에게 공지할 내용을 작성해 주세요.",
                            color = Color(0xFF787878),
                            fontSize = 16.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF145F44),
                        unfocusedBorderColor = Color(0xFFD2D2D2),
                        focusedContainerColor = Color(0xFFF2FAF4),
                        unfocusedContainerColor = Color(0xFFF2FAF4)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
                    maxLines = 6
                )

                // 등록하기 버튼
                Button(
                    onClick = { onConfirm(pushMessage) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF145F44),
                        contentColor = White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = pushMessage.isNotBlank()
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

// 더미 회원 데이터
private fun dummyMemberData(): List<MemberInfo> = listOf(
    MemberInfo("1", "010-1234-1234", "여", "1년 8개월", "1997", "jsoo.kim", "멤버십"),
    MemberInfo("2", "010-1234-1234", "여", "9개월", "1994", "rmfl_stanley", "게스트"),
    MemberInfo("3", "010-5678-5678", "남", "2년 3개월", "1992", "tennis_pro", "멤버십"),
    MemberInfo("4", "010-9999-9999", "남", "6개월", "1999", "beginner_01", "게스트"),
    MemberInfo("5", "010-1111-2222", "여", "3년 1개월", "1988", "tennis_queen", "멤버십")
)
