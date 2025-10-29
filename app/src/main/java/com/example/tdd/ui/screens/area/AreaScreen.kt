package com.example.tdd.ui.screens.area

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tdd.model.area.Area
import com.example.tdd.ui.common.ResourceState
import com.example.tdd.viewmodel.area.AreaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AreaScreen(viewModel: AreaViewModel){
    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("지역 코드") }) }
    ) { padding ->
        AreaListContent(
            modifier = Modifier.padding(padding),
            state = state.value,
            onRetry = { viewModel.refresh() },
            onItemClick = { area ->
                Log.d("AreaScreen", "click area = $area")
            }
        )
    }
}

@Composable
fun AreaListContent(
    modifier: Modifier = Modifier,
    state: ResourceState<List<Area>>,
    onRetry: () -> Unit,
    onItemClick: (Area) -> Unit
) {
    when (state) {
        is ResourceState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .testTag("loading"),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        }
        is ResourceState.Error -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .testTag("error"),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(state.message, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(12.dp))
                Button(onClick = onRetry) { Text("다시 시도") }
            }
        }
        is ResourceState.Success -> {
            val items = state.data
            if (items.isEmpty()) {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .testTag("empty"),
                    contentAlignment = Alignment.Center
                ) { Text("데이터가 없습니다") }
            } else {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .testTag("list"),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items) { area ->
                        AreaRow(area = area) { onItemClick(area) }
                    }
                }
            }
        }
    }
}

@Composable
private fun AreaRow(area: Area, onClick: () -> Unit) {
    Surface(tonalElevation = 1.dp, shape = MaterialTheme.shapes.medium) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(16.dp)
                .testTag("row_${area.code}"),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("${area.code}", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.width(12.dp))
            Text(
                area.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}