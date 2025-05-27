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

    val pagerState = rememberPagerState(pageCount = { locations.size })

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentPadding = PaddingValues(horizontal = 40.dp),
        pageSpacing = 16.dp
    ) { page ->
        CheckInCard(location = locations[page])
    }
}

@Composable
fun CheckInCard(
    location: CheckInLocation
) {
    Card(
        modifier = Modifier
            .width(474.dp)
            .height(818.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp, vertical = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "출석 체크",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 20.dp)
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
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                lineHeight = 28.sp,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            // Location Info Box
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(61.dp)
                    .background(Color(0xFFF2FAF4), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = location.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Text(
                        text = location.court,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // QR Code Area
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp)
                    .background(Color(0xFFF3F3F3), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                // QR 코드가 여기에 들어갈 예정
            }

            Spacer(modifier = Modifier.height(20.dp))

            // QR Instruction Text
            Text(
                text = "QR코드를 찍어주세요",
                fontSize = 20.02.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF555555)
            )
        }
    }
}