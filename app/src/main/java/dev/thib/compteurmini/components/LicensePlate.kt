package dev.thib.compteurmini.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun CreateAlertDialogLicencePlate(
    dialogTitle: String,
    confirmButtonText: String = "Confirmer",
    dismissButtonText: String = "Annuler",
    textFieldValue: String = "",
    onLicensePlateChange: (String) -> Unit = {},
    onDismissRequest: () -> Unit = {},
    onConfirmRequest: (String) -> Unit = {}
) {
    var licensePlate by remember {
        mutableStateOf(textFieldValue)
    }

    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        title = { Text(text = dialogTitle) },
        text = {
            Column {
                LicensePlateInput(
                    licensePlate = licensePlate,
                    onLicensePlateChange = { newLicensePlate ->
                        val formattedLicensePlate = newLicensePlate.take(9).uppercase() // Limiter à 9 caractères et convertir en majuscules
                        licensePlate = formattedLicensePlate
                        onLicensePlateChange(formattedLicensePlate)
                    }
                )
            }
        },
        confirmButton = {
            CreateButton(buttonText = confirmButtonText, enabled = verifierPlaqueImmatriculation(licensePlate)) {
                if (verifierPlaqueImmatriculation(licensePlate)) {
                    onConfirmRequest(licensePlate)
                }
            }
        },
        dismissButton = {
            CreateButton(buttonText = dismissButtonText) {
                onDismissRequest()
            }
        }
    )
}

@Composable
fun LicensePlateInput(
    licensePlate: String,
    onLicensePlateChange: (String) -> Unit
) {
    OutlinedTextField(
        value = licensePlate,
        onValueChange = onLicensePlateChange,
        label = { Text(text = "Plaque immatriculation") },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text
        ),
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1
    )
}

fun verifierPlaqueImmatriculation(plaque: String): Boolean {
    // Expression régulière pour valider un numéro de plaque française avec jusqu'à deux "?" n'importe où
    val regexPlaque = Regex("^[A-Z]{2}-\\d{3}-[A-Z]{2}(\\?{1,2})?$")

    // Retourne true si le numéro de plaque est valide, sinon false
    return regexPlaque.matches(plaque)
}
