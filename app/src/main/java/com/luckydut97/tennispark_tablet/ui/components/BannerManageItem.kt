package com.luckydut97.tennispark_tablet.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class BannerItemUiState(
    val imageUrl: String? = null,
    val linkUrl: String? = null,
    val isEditing: Boolean = false
)

@Composable
fun BannerManageItem(
    index: Int,
    state: BannerItemUiState,
    onEditClick: () -> Unit,
    onSaveClick: (String) -> Unit,
    onCancelClick: () -> Unit,
    onImageClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val shape = RoundedCornerShape(6.dp)
    var urlInput by remember(state.isEditing, state.linkUrl) {
        mutableStateOf(state.linkUrl ?: "")
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "광고 ${index + 1}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )

            if (state.isEditing) {
                Spacer(Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .width(259.dp)
                        .height(40.dp)
                        .background(Color.White, shape)
                        .border(0.91.dp, Color(0xFF08432E), shape)
                        .padding(horizontal = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (urlInput.isEmpty()) {
                        Text(
                            text = "광고배너 URL 주소를 입력해주세요",
                            color = Color(0xFF787878),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    BasicTextField(
                        value = urlInput,
                        onValueChange = { urlInput = it },
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(Modifier.width(10.dp))
                Button(
                    onClick = { onSaveClick(urlInput) },
                    modifier = Modifier.size(width = 68.dp, height = 40.dp),
                    shape = shape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF08432E),
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("저장", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                Spacer(Modifier.width(10.dp))
                OutlinedButton(
                    onClick = onCancelClick,
                    modifier = Modifier.size(width = 68.dp, height = 40.dp),
                    shape = shape,
                    border = BorderStroke(0.91.dp, Color(0xFF08432E)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFF08432E)
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("취소", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            } else {
                OutlinedButton(
                    onClick = onEditClick,
                    modifier = Modifier.size(width = 68.dp, height = 40.dp),
                    shape = shape,
                    border = BorderStroke(0.91.dp, Color(0xFF08432E)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFF08432E)
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("수정", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                Spacer(Modifier.width(10.dp))
                OutlinedButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.size(width = 68.dp, height = 40.dp),
                    shape = shape,
                    border = BorderStroke(0.91.dp, Color(0xFF08432E)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFF08432E)
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("삭제", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }

        Spacer(Modifier.height(11.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 1f)
                .background(Color(0xFFF5F5F5), shape)
                .clip(shape)
                .clickable { onImageClick() },
            contentAlignment = Alignment.Center
        ) {
            // 이미지가 있으면 표시, 없으면 빈 박스
            if (state.imageUrl != null) {
                // TODO: 실제 이미지 로딩 구현
            }
        }
    }
}
