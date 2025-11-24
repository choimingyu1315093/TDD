package com.example.tdd.ui.screens.book

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tdd.R

data class BookCompletionUiState(
    val orderText: String,
    val periodText: String,
    val title: String,
    val author: String,
    @DrawableRes val coverRes: Int
)

@Composable
fun BookCompletionScreen(
    state: BookCompletionUiState,
    modifier: Modifier = Modifier,
    onShare: () -> Unit = {},
    onClose: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    val primaryGreen = Color(0xFF0AA53B)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(primaryGreen)
            .statusBarsPadding()
            .padding(horizontal = 32.dp, vertical = 16.dp)
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                tint = Color.White,
                contentDescription = "뒤로가기"
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = state.orderText,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = state.periodText,
                color = Color.White.copy(alpha = 0.9f),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(32.dp))
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 12.dp
            ) {
                Image(
                    painter = painterResource(id = state.coverRes),
                    contentDescription = state.title,
                    modifier = Modifier
                        .size(width = 160.dp, height = 220.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
            Spacer(Modifier.height(24.dp))
            Text(
                text = state.title,
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = state.author,
                color = Color.White.copy(alpha = 0.85f),
                style = MaterialTheme.typography.titleMedium
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onShare,
                modifier = Modifier
                    .width(220.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = primaryGreen
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("기록 공유하기", fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(16.dp))
            TextButton(onClick = onClose) {
                Text(
                    text = "닫기",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview(name = "BookCompletion", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun BookCompletionScreenPreview() {
    BookCompletionScreen(
        state = BookCompletionUiState(
            orderText = "올해의 1번째 도서 완독!",
            periodText = "2024-04-01~2024-04-02",
            title = "돈의 속성",
            author = "홍길동",
            coverRes = R.drawable.ic_launcher_foreground
        )
    )
}

