package com.luckydut97.tennispark_tablet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameRecordBottomSheet(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String, String, String) -> Unit
) {
    var teamA1 by remember { mutableStateOf("") }
    var teamA2 by remember { mutableStateOf("") }
    var teamB1 by remember { mutableStateOf("") }
    var teamB2 by remember { mutableStateOf("") }
    var scoreA by remember { mutableStateOf("") }
    var scoreB by remember { mutableStateOf("") }

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
                    text = "경기 기록 하기",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                // A팀 선수 이름
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "A팀",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF262626)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color.White, RoundedCornerShape(12.dp))
                                .border(1.dp, Color(0xFFD2D2D2), RoundedCornerShape(12.dp))
                                .padding(horizontal = 16.dp)
                        ) {
                            BasicTextField(
                                value = teamA1,
                                onValueChange = { teamA1 = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 56.dp),
                                textStyle = TextStyle(
                                    color = Color(0xFF262626),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Normal,
                                    lineHeight = 30.sp
                                ),
                                singleLine = false,
                                maxLines = 1,
                                decorationBox = { inner ->
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .defaultMinSize(minHeight = 56.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        if (teamA1.isEmpty()) {
                                            Text(
                                                text = "선수 이름을 입력해주세요.",
                                                color = Color(0xFF787878),
                                                fontSize = 22.sp,
                                                fontWeight = FontWeight.Normal
                                            )
                                        }
                                        inner()
                                    }
                                }
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color.White, RoundedCornerShape(12.dp))
                                .border(1.dp, Color(0xFFD2D2D2), RoundedCornerShape(12.dp))
                                .padding(horizontal = 16.dp)
                        ) {
                            BasicTextField(
                                value = teamA2,
                                onValueChange = { teamA2 = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 56.dp),
                                textStyle = TextStyle(
                                    color = Color(0xFF262626),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Normal,
                                    lineHeight = 30.sp
                                ),
                                singleLine = false,
                                maxLines = 1,
                                decorationBox = { inner ->
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .defaultMinSize(minHeight = 56.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        if (teamA2.isEmpty()) {
                                            Text(
                                                text = "선수 이름을 입력해주세요.",
                                                color = Color(0xFF787878),
                                                fontSize = 22.sp,
                                                fontWeight = FontWeight.Normal
                                            )
                                        }
                                        inner()
                                    }
                                }
                            )
                        }
                    }
                }

                // B팀 선수 이름
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "B팀",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF262626)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color.White, RoundedCornerShape(12.dp))
                                .border(1.dp, Color(0xFFD2D2D2), RoundedCornerShape(12.dp))
                                .padding(horizontal = 16.dp)
                        ) {
                            BasicTextField(
                                value = teamB1,
                                onValueChange = { teamB1 = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 56.dp),
                                textStyle = TextStyle(
                                    color = Color(0xFF262626),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Normal,
                                    lineHeight = 30.sp
                                ),
                                singleLine = false,
                                maxLines = 1,
                                decorationBox = { inner ->
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .defaultMinSize(minHeight = 56.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        if (teamB1.isEmpty()) {
                                            Text(
                                                text = "선수 이름을 입력해주세요.",
                                                color = Color(0xFF787878),
                                                fontSize = 22.sp,
                                                fontWeight = FontWeight.Normal
                                            )
                                        }
                                        inner()
                                    }
                                }
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color.White, RoundedCornerShape(12.dp))
                                .border(1.dp, Color(0xFFD2D2D2), RoundedCornerShape(12.dp))
                                .padding(horizontal = 16.dp)
                        ) {
                            BasicTextField(
                                value = teamB2,
                                onValueChange = { teamB2 = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 56.dp),
                                textStyle = TextStyle(
                                    color = Color(0xFF262626),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Normal,
                                    lineHeight = 30.sp
                                ),
                                singleLine = false,
                                maxLines = 1,
                                decorationBox = { inner ->
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .defaultMinSize(minHeight = 56.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        if (teamB2.isEmpty()) {
                                            Text(
                                                text = "선수 이름을 입력해주세요.",
                                                color = Color(0xFF787878),
                                                fontSize = 22.sp,
                                                fontWeight = FontWeight.Normal
                                            )
                                        }
                                        inner()
                                    }
                                }
                            )
                        }
                    }
                }

                // 점수
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "점수",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF262626)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color.White, RoundedCornerShape(12.dp))
                                .border(1.dp, Color(0xFFD2D2D2), RoundedCornerShape(12.dp))
                                .padding(horizontal = 16.dp)
                        ) {
                            BasicTextField(
                                value = scoreA,
                                onValueChange = { scoreA = it.filter { c -> c.isDigit() } },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 56.dp),
                                textStyle = TextStyle(
                                    color = Color(0xFF262626),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Normal,
                                    lineHeight = 30.sp
                                ),
                                singleLine = true,
                                maxLines = 1,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                decorationBox = { inner ->
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .defaultMinSize(minHeight = 56.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        if (scoreA.isEmpty()) {
                                            Text(
                                                text = "A팀 점수",
                                                color = Color(0xFF787878),
                                                fontSize = 22.sp,
                                                fontWeight = FontWeight.Normal
                                            )
                                        }
                                        inner()
                                    }
                                }
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color.White, RoundedCornerShape(12.dp))
                                .border(1.dp, Color(0xFFD2D2D2), RoundedCornerShape(12.dp))
                                .padding(horizontal = 16.dp)
                        ) {
                            BasicTextField(
                                value = scoreB,
                                onValueChange = { scoreB = it.filter { c -> c.isDigit() } },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 56.dp),
                                textStyle = TextStyle(
                                    color = Color(0xFF262626),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Normal,
                                    lineHeight = 30.sp
                                ),
                                singleLine = true,
                                maxLines = 1,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                decorationBox = { inner ->
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .defaultMinSize(minHeight = 56.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        if (scoreB.isEmpty()) {
                                            Text(
                                                text = "B팀 점수",
                                                color = Color(0xFF787878),
                                                fontSize = 22.sp,
                                                fontWeight = FontWeight.Normal
                                            )
                                        }
                                        inner()
                                    }
                                }
                            )
                        }
                    }
                }

                // 기록하기 버튼
                Button(
                    onClick = {
                        onConfirm(teamA1, teamA2, teamB1, teamB2, scoreA, scoreB)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(47.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF145F44),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "기록 하기",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
