package com.luckydut97.tennispark_tablet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckydut97.tennispark_tablet.ui.components.BottomNavigationBar
import com.luckydut97.tennispark_tablet.ui.components.TabletTopBar
import com.luckydut97.tennispark_tablet.ui.theme.*

data class ApplicationInfo(
    val no: String,
    val id: String,
    val name: String,
    val type: String,
    val status: String
)

@Composable
fun ApplicationListScreen(
    selectedItem: String,
    onNavigateToHome: () -> Unit,
    onNavigateToActivity: () -> Unit,
    onNavigateToEvent: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateBack: () -> Unit
) {
    // 테스트 데이터 20개
    val applications = remember {
        (1..20).map { index ->
            ApplicationInfo(
                no = index.toString(),
                id = "010-1234-1234",
                name = "홍길동",
                type = "멤버십",
                status = "확정"
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TennisGreen),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabletTopBar(title = "신청 명단", onBack = onNavigateBack)

        // 고정 크기 754×841 흰색 박스
        Card(
            modifier = Modifier
                .width(754.dp)
                .height(841.dp)
                .padding(vertical = 20.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                // 테이블 헤더
                ApplicationTableHeader()
                
                // 구분선
                ApplicationTableDivider()

                // 테이블 내용 (최대 15개 보이고 스크롤)
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    itemsIndexed(applications.take(15)) { index, application ->
                        ApplicationTableRow(application = application)
                        if (index < 14) { // 15개 중 마지막이 아닐 때만 구분선
                            ApplicationTableDivider()
                        }
                    }
                    
                    // 15개 이상일 때 추가 데이터는 스크롤로
                    if (applications.size > 15) {
                        itemsIndexed(applications.drop(15)) { index, application ->
                            ApplicationTableDivider()
                            ApplicationTableRow(application = application)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        BottomNavigationBar(
            selectedItem = selectedItem,
            onNavigateToHome = onNavigateToHome,
            onNavigateToActivity = onNavigateToActivity,
            onNavigateToEvent = onNavigateToEvent,
            onNavigateToSettings = onNavigateToSettings
        )
    }
}

@Composable
fun ApplicationTableHeader() {
    val columnWeights = listOf(0.1f, 0.25f, 0.2f, 0.2f, 0.25f)
    val titles = listOf("NO", "ID", "이름", "구분", "비고")
    val headerColor = Color(0xFF262626)
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        titles.forEachIndexed { idx, title ->
            Box(
                modifier = Modifier.weight(columnWeights[idx]),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    color = headerColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ApplicationTableRow(application: ApplicationInfo) {
    val columnWeights = listOf(0.1f, 0.25f, 0.2f, 0.2f, 0.25f)
    var selectedStatus by remember { mutableStateOf(application.status) }
    var expanded by remember { mutableStateOf(false) }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // NO
        Box(
            modifier = Modifier.weight(columnWeights[0]),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = application.no,
                color = Color(0xFF787878),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
        
        // ID
        Box(
            modifier = Modifier.weight(columnWeights[1]),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = application.id,
                color = Color(0xFF787878),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
        
        // 이름
        Box(
            modifier = Modifier.weight(columnWeights[2]),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = application.name,
                color = Color(0xFF787878),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
        
        // 구분
        Box(
            modifier = Modifier.weight(columnWeights[3]),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = application.type,
                color = Color(0xFF787878),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
        
        // 비고 (드롭다운 - 좌측 정렬 + 아래 화살표)
        Box(
            modifier = Modifier.weight(columnWeights[4]),
            contentAlignment = Alignment.Center
        ) {
            Box {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier
                        .width(120.dp)
                        .height(36.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black // 비고 버튼 텍스트 색상 변경
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.Black), // 비고 버튼 테두리 색상 변경
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedStatus,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Start,
                            color = Color.Black // 비고 텍스트 색상 변경
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "드롭다운",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Black // 비고 아이콘 색상 변경
                        )
                    }
                }
                
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color.White) // 드롭다운 메뉴 배경색 흰색
                ) {
                    DropdownMenuItem(
                        text = { Text("확정", color = Color.Black) }, // 드롭다운 아이템 텍스트 검정색
                        onClick = {
                            selectedStatus = "확정"
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("미확정", color = Color.Black) }, // 드롭다운 아이템 텍스트 검정색
                        onClick = {
                            selectedStatus = "미확정"
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ApplicationTableDivider() {
    Box(
        modifier = Modifier
            .width(714.dp)
            .height(1.dp)
            .background(Color(0xFFE0E0E0))
    )
}

@Preview(showBackground = true, widthDp = 1600, heightDp = 2560)
@Composable
fun ApplicationListScreenPreview() {
    Tennispark_tabletTheme {
        ApplicationListScreen(
            selectedItem = "활동 관리",
            onNavigateToHome = {},
            onNavigateToActivity = {},
            onNavigateToEvent = {},
            onNavigateToSettings = {},
            onNavigateBack = {}
        )
    }
}
