package com.luckydut97.tennispark_tablet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.luckydut97.tennispark_tablet.ui.theme.*
import com.luckydut97.tennispark_tablet.data.network.event.EventItem

data class CheckInEvent(
    val title: String,
    val content: String,
    val points: Int,
    val imageUrl: String?
)

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun CheckInCardPager(events: List<EventItem> = emptyList()) {
    // API 데이터가 없으면 기본 데이터 사용
    val eventItems = if (events.isEmpty()) {
        listOf(
            CheckInEvent("이벤트를 등록해주세요", "이벤트 등록 후 참여 가능합니다", 0, null)
        )
    } else {
        events.map { event ->
            CheckInEvent(event.title, event.content, event.point, event.imageUrl)
        }
    }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    // 반응형 크기 계산
    val cardWidth = (screenWidth * 0.55f).coerceAtMost(474.dp).coerceAtLeast(320.dp)
    val cardHeight = (screenHeight * 0.58f).coerceAtMost(900.dp).coerceAtLeast(700.dp)
    val horizontalPadding = ((screenWidth - cardWidth) / 2).coerceAtLeast(20.dp)

    val pagerState = rememberPagerState(pageCount = { eventItems.size })

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentPadding = PaddingValues(horizontal = horizontalPadding),
        pageSpacing = (screenWidth * 0.05f).coerceAtLeast(20.dp).coerceAtMost(70.dp)
    ) { page ->
        CheckInCard(
            event = eventItems[page],
            cardWidth = cardWidth,
            cardHeight = cardHeight
        )
    }
}

@Composable
fun CheckInCard(
    event: CheckInEvent,
    cardWidth: androidx.compose.ui.unit.Dp = 474.dp,
    cardHeight: androidx.compose.ui.unit.Dp = 850.dp
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    // ========== 크기 조절 변수들 (여기서 한 번에 조절 가능) ==========
    val qrBoxSizeRatio = 0.65f           // QR 박스 크기 비율 (카드 너비 대비)
    val eventBoxHeightRatio = 0.09f      // 이벤트 내용 박스 높이 비율 (카드 높이 대비)

    // 계산된 크기들
    val qrBoxSize = (cardWidth * qrBoxSizeRatio).coerceAtLeast(300.dp).coerceAtMost(400.dp)
    val eventBoxHeight = (cardHeight * eventBoxHeightRatio).coerceAtLeast(55.dp)
    // ===============================================================

    // 반응형 폰트 크기 계산 - 모든 폰트 크기 증가
    val titleFontSize = (screenWidth.value * 0.025f).coerceAtLeast(28f).coerceAtMost(38f).sp
    val descriptionFontSize = (screenWidth.value * 0.014f).coerceAtLeast(18f).coerceAtMost(22f).sp
    val eventNameFontSize = (screenWidth.value * 0.014f).coerceAtLeast(18f).coerceAtMost(22f).sp
    val pointsFontSize = (screenWidth.value * 0.017f).coerceAtLeast(20f).coerceAtMost(26f).sp
    val qrTextFontSize = (screenWidth.value * 0.014f).coerceAtLeast(18f).coerceAtMost(22f).sp

    // 반응형 패딩 및 크기 - 여백도 조금 증가
    val cardPadding = (cardWidth.value * 0.09f).coerceAtLeast(28f).coerceAtMost(44f).dp

    Card(
        modifier = Modifier
            .width(cardWidth)
            .height(cardHeight),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = cardPadding,
                    vertical = (cardHeight * 0.071f).coerceAtLeast(30.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = event.title,
                fontSize = titleFontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = (cardHeight * 0.029f).coerceAtLeast(15.dp))
            )

            // Description with styled text
            val annotatedText = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color(0xFF555555))) {
                    append("이벤트에 참여 하시면\n")
                }
                withStyle(style = SpanStyle(color = Color(0xFF555555), fontWeight = FontWeight.Bold)) {
                    append("${event.points} Point")
                }
                withStyle(style = SpanStyle(color = Color(0xFF555555))) {
                    append(" 지급해드립니다.")
                }
            }

            Text(
                text = annotatedText,
                fontSize = descriptionFontSize,
                textAlign = TextAlign.Center,
                lineHeight = (descriptionFontSize.value * 1.4f).sp,
                modifier = Modifier.padding(bottom = (cardHeight * 0.057f).coerceAtLeast(25.dp))
            )

            // Event Info Box
            Box(
                modifier = Modifier
                    .width(qrBoxSize)
                    .height(eventBoxHeight)
                    .background(Color(0xFFF2FAF4), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        (qrBoxSize * 0.067f).coerceAtLeast(
                            12.dp
                        )
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = event.content,
                        fontSize = eventNameFontSize,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height((cardHeight * 0.057f).coerceAtLeast(35.dp)))

            // QR Code Area
            Box(
                modifier = Modifier
                    .size(qrBoxSize)
                    .background(Color(0xFFF3F3F3), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (event.imageUrl != null) {
                    AsyncImage(
                        model = event.imageUrl,
                        contentDescription = "QR 코드",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                } else {
                    // QR 이미지가 없을 때 표시할 기본 텍스트
                    Text(
                        text = "QR 이미지 없음",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height((cardHeight * 0.043f).coerceAtLeast(25.dp)))

            // QR Instruction Text
            Text(
                text = "QR코드를 찍어주세요",
                fontSize = qrTextFontSize,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF555555)
            )
        }
    }
}
