package com.luckydut97.tennispark_tablet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
        AlertDialog(
            onDismissRequest = { showDeleteDialogFor = null },
            title = { Text("광고 삭제") },
            text = { Text("광고를 삭제하시겠습니까?") },
            confirmButton = {
                Button(onClick = {
                    bannerStates = bannerStates.mapIndexed { i, item ->
                        if (i == showDeleteDialogFor)
                            BannerItemUiState()
                        else item
                    }
                    showDeleteDialogFor = null
                }) {
                    Text("확인")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialogFor = null }) {
                    Text("취소")
                }
            }
        )
    }
}