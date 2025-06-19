package com.luckydut97.tennispark_tablet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckydut97.tennispark_tablet.ui.components.TabletTopBar
import com.luckydut97.tennispark_tablet.ui.components.BannerManageItem
import com.luckydut97.tennispark_tablet.ui.components.BannerItemUiState

enum class BannerManageType(val title: String) {
    MAIN("메인화면 배너 관리"),
    ACTIVITY("활동신청 배너 관리"),
    PURCHASE("구매목록 배너 관리"),
    MEMBER("회원정보 배너 관리"),
}

@Composable
fun BannerManageScreen(
    manageType: BannerManageType,
    onBack: () -> Unit
) {
    var bannerStates by remember {
        mutableStateOf(
            List(3) { BannerItemUiState() }
        )
    }
    var showDeleteDialogFor by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TabletTopBar(title = manageType.title, onBack = onBack)

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 40.dp)
                .padding(top = 20.dp)
        ) {
            repeat(3) { index ->
                BannerManageItem(
                    index = index,
                    state = bannerStates[index],
                    onEditClick = {
                        bannerStates = bannerStates.mapIndexed { i, item ->
                            if (i == index) item.copy(isEditing = true) else item.copy(isEditing = false)
                        }
                    },
                    onSaveClick = { url ->
                        bannerStates = bannerStates.mapIndexed { i, item ->
                            if (i == index) item.copy(linkUrl = url, isEditing = false) else item
                        }
                    },
                    onCancelClick = {
                        bannerStates = bannerStates.mapIndexed { i, item ->
                            if (i == index) item.copy(isEditing = false) else item
                        }
                    },
                    onImageClick = {
                        // TODO: 이미지 업로드 구현
                    },
                    onDeleteClick = { showDeleteDialogFor = index }
                )
                if (index < 2) Spacer(Modifier.height(40.dp))
            }
            Spacer(Modifier.height(40.dp))
        }
    }

    // 삭제 확인 다이얼로그
    if (showDeleteDialogFor != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable { showDeleteDialogFor = null },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(width = 354.dp, height = 186.dp)
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(24.dp)
                    .clickable { /* 내부 클릭 시 아무것도 하지 않음 */ },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "광고 삭제",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "광고를 삭제하시겠습니까?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF787878),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Button(
                        onClick = {
                            bannerStates = bannerStates.mapIndexed { i, item ->
                                if (i == showDeleteDialogFor)
                                    BannerItemUiState()
                                else item
                            }
                            showDeleteDialogFor = null
                        },
                        modifier = Modifier.size(width = 309.dp, height = 55.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF145F44),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(
                            text = "확인",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
