package dev.thib.compteurmini

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.thib.compteurmini.ui.theme.CompteurMiniTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompteurMiniTheme {
                MainScreen()
            }
        }
    }
}


