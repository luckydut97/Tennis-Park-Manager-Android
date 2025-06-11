package com.luckydut97.tennispark_tablet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckydut97.tennispark_tablet.R

val MemberInfoTitleColor = Color.White
val MemberInfoLabelColor = Color(0xFF787878)
val MemberInfoFieldBg = Color.White
val MemberInfoTableBorder = Color(0xFFD2D2D2)

// Data model
data class MemberInfo(
    val no: String,
    val id: String,
    val gender: String,
    val period: String,
    val age: String,
    val instaId: String,
    val type: String
)

@Composable
fun MemberInfoSection(
    memberList: List<MemberInfo> = dummyMemberData(),
    onSearch: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var searchText by remember { mutableStateOf("") }
    var searchExecuted by remember { mutableStateOf(false) }
    var displayedList by remember { mutableStateOf(memberList) }

    Column(modifier = modifier.width(754.dp)) {
        Text(
            text = "회원정보",
            color = MemberInfoTitleColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                searchExecuted = false
            },
            modifier = Modifier
                .width(754.dp)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            placeholder = {
                Text(
                    text = "검색하고자 하는 회원의 이름을 검색해주세요.",
                    fontSize = 22.sp,
                    color = MemberInfoLabelColor
                )
            },
            textStyle = TextStyle(fontSize = 22.sp, color = Color.Black),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedContainerColor = MemberInfoFieldBg,
                focusedContainerColor = MemberInfoFieldBg
            ),
            trailingIcon = {
                IconButton(
                    onClick = {
                        searchExecuted = true
                        displayedList = if (searchText.isEmpty()) {
                            memberList
                        } else {
                            memberList.filter {
                                it.instaId.contains(searchText, true)
                            }
                        }
                        onSearch(searchText)
                    },
                    modifier = Modifier.padding(end = 30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "검색",
                        tint = MemberInfoLabelColor
                    )
                }
            }
        )
        Spacer(Modifier.height(10.dp))
        // 검색 전엔 아무것도 안 보임, 검색했을 때만 테이블(헤더+구분선+결과orEmpty) 노출
        if (searchExecuted) {
            Box(
                modifier = Modifier
                    .height(170.dp)
                    .width(754.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    Modifier
                        .background(MemberInfoFieldBg, RoundedCornerShape(12.dp))
                        .fillMaxSize()
                        .padding(horizontal = 0.dp, vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MemberTableHeader_Modern()
                    MemberTableDivider()
                    if (displayedList.isEmpty()) {
                        MemberTableEmptyRow()
                    } else {
                        displayedList.forEach { member ->
                            MemberTableRow_Modern(member)
                        }
                    }
                }
            }
        }
    }
}

val pretendard = FontFamily(Font(R.font.pretendard_regular))

@Composable
fun MemberTableHeader_Modern() {
    val columnWidths = listOf(45.dp, 180.dp, 45.dp, 150.dp, 70.dp, 150.dp, 62.dp)
    val titles = listOf("NO", "ID", "성별", "구력", "나이", "인스타ID", "구분")
    val headerColor = Color(0xFF262626)
    Row(
        Modifier
            .height(26.dp), // 오타! 반드시 데이터행과 동일하게
        verticalAlignment = Alignment.CenterVertically
    ) {
        titles.forEachIndexed { idx, t ->
            Box(
                Modifier.width(columnWidths[idx]),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    t,
                    color = headerColor,
                    fontSize = 18.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun MemberTableRow_Modern(member: MemberInfo) {
    val columnWidths = listOf(45.dp, 180.dp, 45.dp, 150.dp, 70.dp, 150.dp, 62.dp)
    val values = listOf(
        member.no, member.id, member.gender, member.period, member.age, member.instaId, member.type
    )
    Row(
        Modifier.height(26.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        values.forEachIndexed { idx, value ->
            Box(Modifier.width(columnWidths[idx]), contentAlignment = Alignment.Center) {
                Text(
                    text = value,
                    color = Color(0xFF787878),
                    fontSize = 18.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun MemberTableDivider() {
    Box(
        Modifier
            .width(702.dp)
            .height(1.dp)
            .background(MemberInfoTableBorder)
            .padding(vertical = 4.dp)
    )
}

@Composable
fun MemberTableEmptyRow() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(26.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "조회 가능한 회원이 없습니다.",
            color = MemberInfoLabelColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

fun dummyMemberData(): List<MemberInfo> = listOf(
    MemberInfo("1", "010-1234-1234", "남", "1년 8개월", "1997", "jsoo.kim", "멤버십"),
    MemberInfo("2", "010-1234-1234", "여", "9개월", "1994", "rmfl_stanley", "게스트")
)
