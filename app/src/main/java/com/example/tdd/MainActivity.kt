package com.example.tdd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tdd.ui.screens.area.AreaScreen
import com.example.tdd.ui.theme.TDDTheme
import com.example.tdd.viewmodel.area.AreaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TDDTheme {
                val viewModel: AreaViewModel = hiltViewModel()
                AreaScreen(viewModel)
            }
        }
    }
}