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
import com.luckydut97.tennispark_tablet.ui.theme.*

data class CheckInLocation(
    val name: String,
    val court: String
)

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun CheckInCardPager() {
    val locations = listOf(
        CheckInLocation("수도공고 테니스장", "A코트"),
        CheckInLocation("강남 테니스장", "B코트"),
        CheckInLocation("서초 테니스장", "C코트")
    )

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    // 반응형 크기 계산
    val cardWidth = (screenWidth * 0.55f).coerceAtMost(474.dp).coerceAtLeast(320.dp)
    val cardHeight = (screenHeight * 0.65f).coerceAtMost(900.dp).coerceAtLeast(700.dp)
    val horizontalPadding = ((screenWidth - cardWidth) / 2).coerceAtLeast(20.dp)

    val pagerState = rememberPagerState(pageCount = { locations.size })

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentPadding = PaddingValues(horizontal = horizontalPadding),
        pageSpacing = (screenWidth * 0.05f).coerceAtLeast(20.dp).coerceAtMost(70.dp)
    ) { page ->
        CheckInCard(
            location = locations[page],
            cardWidth = cardWidth,
            cardHeight = cardHeight
        )
    }
}

@Composable
fun CheckInCard(
    location: CheckInLocation,
    cardWidth: androidx.compose.ui.unit.Dp = 474.dp,
    cardHeight: androidx.compose.ui.unit.Dp = 850.dp
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    // 반응형 폰트 크기 계산 - 모든 폰트 크기 증가
    val titleFontSize = (screenWidth.value * 0.025f).coerceAtLeast(28f).coerceAtMost(38f).sp
    val descriptionFontSize = (screenWidth.value * 0.014f).coerceAtLeast(18f).coerceAtMost(22f).sp
    val locationFontSize = (screenWidth.value * 0.014f).coerceAtLeast(18f).coerceAtMost(22f).sp
    val courtFontSize = (screenWidth.value * 0.017f).coerceAtLeast(20f).coerceAtMost(26f).sp
    val qrTextFontSize = (screenWidth.value * 0.014f).coerceAtLeast(18f).coerceAtMost(22f).sp

    // 반응형 패딩 및 크기 - 여백도 조금 증가
    val cardPadding = (cardWidth.value * 0.09f).coerceAtLeast(28f).coerceAtMost(44f).dp
    val locationBoxWidth = (cardWidth * 0.65f).coerceAtLeast(250.dp)
    val locationBoxHeight = (cardHeight * 0.09f).coerceAtLeast(55.dp)
    val qrBoxSize = (cardWidth * 0.75f).coerceAtLeast(300.dp).coerceAtMost(400.dp)

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
                text = "출석 체크",
                fontSize = titleFontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = (cardHeight * 0.029f).coerceAtLeast(15.dp))
            )

            // Description with styled text
            val annotatedText = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color(0xFF555555))) {
                    append("이용하려는 시설의 QR 코드로 체크인하면\n")
                }
                withStyle(style = SpanStyle(color = Color(0xFF555555), fontWeight = FontWeight.Bold)) {
                    append("100 Point")
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

            // Location Info Box
            Box(
                modifier = Modifier
                    .width(locationBoxWidth)
                    .height(locationBoxHeight)
                    .background(Color(0xFFF2FAF4), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy((locationBoxWidth * 0.067f).coerceAtLeast(12.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = location.name,
                        fontSize = locationFontSize,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Text(
                        text = location.court,
                        fontSize = courtFontSize,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
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
                // QR 코드가 여기에 들어갈 예정
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
