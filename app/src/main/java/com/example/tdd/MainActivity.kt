package com.example.tdd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.tdd.ui.screens.area.AreaScreen
import com.example.tdd.ui.theme.TDDTheme
import com.example.tdd.viewmodel.area.AreaViewModel
import com.example.tdd.viewmodel.area.AreaViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: AreaViewModel by viewModels {
        AreaViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TDDTheme {
                AreaScreen(viewModel)
            }
        }
    }
}