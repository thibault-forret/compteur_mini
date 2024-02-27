package dev.thib.compteurmini.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CreateButton(
    buttonText: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled
    ) {
        Text(text = buttonText)
    }
}