package com.luckydut97.tennispark_tablet.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.border
import com.luckydut97.tennispark_tablet.ui.screens.TabletEventItem

@Composable
fun EventEditBottomSheet(
    event: TabletEventItem,
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onUpdate: (String, String, String) -> Unit
) {
    var eventName by remember { mutableStateOf(event.title) }
    var eventContent by remember { mutableStateOf("수동공고 A코트") } // 예시 텍스트
    var eventPoints by remember { mutableStateOf(event.points.toString()) }

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
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 제목
                Text(
                    text = "이벤트 수정",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                // 이벤트명
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "이벤트명",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF262626)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(12.dp))
                            .border(1.dp, Color(0xFFD2D2D2), RoundedCornerShape(12.dp))
                            .padding(horizontal = 16.dp)
                    ) {
                        BasicTextField(
                            value = eventName,
                            onValueChange = { eventName = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 56.dp)
                                .align(Alignment.CenterStart),
                            textStyle = TextStyle(
                                color = Color(0xFF262626),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 30.sp
                            ),
                            maxLines = 3,
                            singleLine = false,
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .defaultMinSize(minHeight = 56.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (eventName.isEmpty()) {
                                        Text(
                                            text = "이벤트 명을 입력해주세요",
                                            color = Color(0xFF787878),
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.Normal,
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                    }
                }

                // 이벤트 내용
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "이벤트 내용",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF262626)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(12.dp))
                            .border(1.dp, Color(0xFFD2D2D2), RoundedCornerShape(12.dp))
                            .padding(horizontal = 16.dp)
                    ) {
                        BasicTextField(
                            value = eventContent,
                            onValueChange = { eventContent = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 56.dp)
                                .align(Alignment.CenterStart),
                            textStyle = TextStyle(
                                color = Color(0xFF262626),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 30.sp
                            ),
                            maxLines = 5,
                            singleLine = false,
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .defaultMinSize(minHeight = 56.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (eventContent.isEmpty()) {
                                        Text(
                                            text = "이벤트 내용을 입력해주세요",
                                            color = Color(0xFF787878),
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.Normal,
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                    }
                }

                // 지급 포인트
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "지급 포인트",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF262626)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(12.dp))
                            .border(1.dp, Color(0xFFD2D2D2), RoundedCornerShape(12.dp))
                            .padding(horizontal = 16.dp)
                    ) {
                        BasicTextField(
                            value = eventPoints,
                            onValueChange = { newValue ->
                                if (newValue.all { it.isDigit() }) {
                                    eventPoints = newValue
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 56.dp)
                                .align(Alignment.CenterStart),
                            textStyle = TextStyle(
                                color = Color(0xFF262626),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 30.sp
                            ),
                            maxLines = 1,
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .defaultMinSize(minHeight = 56.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (eventPoints.isEmpty()) {
                                        Text(
                                            text = "지급 포인트를 입력해주세요",
                                            color = Color(0xFF787878),
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.Normal,
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                    }
                }

                // 버튼들
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDelete,
                        modifier = Modifier
                            .weight(1f)
                            .height(47.dp),
                        shape = RoundedCornerShape(6.dp),
                        border = BorderStroke(1.dp, Color(0xFFD2D2D2)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF262626)
                        )
                    ) {
                        Text(
                            text = "삭제하기",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF262626)
                        )
                    }

                    Button(
                        onClick = { onUpdate(eventName, eventContent, eventPoints) },
                        modifier = Modifier
                            .weight(1f)
                            .height(47.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF145F44),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(6.dp),
                        enabled = eventName.isNotBlank() && eventContent.isNotBlank() && eventPoints.isNotBlank()
                    ) {
                        Text(
                            text = "수정하기",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
