package com.luckydut97.tennispark_tablet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
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

    // 네비게이션바 높이 고려
    val navBarHeight = 110.dp

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
            TabletTopBar(title = "설정", onBack = onNavigateToHome)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 40.dp)
                    .padding(top = 20.dp), // 상단 패딩만 추가
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
                Spacer(modifier = Modifier.height(30.dp)) // 줄인 Spacer

                // 앱 푸시 버튼
                Button(
                    onClick = { showPushDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(59.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFFF57),
                        contentColor = Color(0xFF145F44)
                    ),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Text(
                        text = "앱 푸시",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF08432E)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp)) // 하단 패딩
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
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 드래그 핸들
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height(4.dp)
                        .background(Color(0xFFE0E0E0), RoundedCornerShape(2.dp))
                )
                
                Spacer(modifier = Modifier.height(20.dp))
                
                // 제목
                Text(
                    text = "앱 푸시",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // 부제목
                    Text(
                        text = "공지사항 안내",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF262626),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // 메시지 입력 필드
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent, RoundedCornerShape(12.dp))
                            .border(
                                width = 1.dp,
                                color = Color(0xFFD2D2D2),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 0.dp)
                    ) {
                        BasicTextField(
                            value = pushMessage,
                            onValueChange = { pushMessage = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 56.dp)
                                .align(Alignment.CenterStart),
                            textStyle = TextStyle(
                                color = Color(0xFF262626),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 30.sp // Pretendard 기본 lineHeight와 유사
                            ),
                            maxLines = 10,
                            singleLine = false,
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .defaultMinSize(minHeight = 56.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (pushMessage.isEmpty()) {
                                        Text(
                                            text = "회원들에게 공지할 내용을 작성해 주세요.",
                                            color = Color(0xFF787878),
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.Normal,
                                            // Pretendard 가정
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 등록하기 버튼
                Button(
                    onClick = { onConfirm(pushMessage) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF145F44),
                        contentColor = White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "등록하기",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,

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
