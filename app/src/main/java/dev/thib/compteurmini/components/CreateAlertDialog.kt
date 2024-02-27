package dev.thib.compteurmini.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CreateAlertDialog(
    dialogTitle: String,
    dialogText: String,
    confirmButtonText: String = "Confirmer",
    dismissButtonText: String = "Annuler",
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogText) },
        confirmButton = {
            CreateButton(buttonText = confirmButtonText) {
                onConfirmRequest()
            }
        },
        dismissButton = {
            CreateButton(buttonText = dismissButtonText) {
                onDismissRequest()
            }
        }

    )
}