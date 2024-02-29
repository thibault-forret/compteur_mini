package dev.thib.compteurmini.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import dev.thib.compteurmini.AnimatedCounter
import dev.thib.compteurmini.PlaqueImmatriculation
import dev.thib.compteurmini.PlaqueImmatriculationDatabaseHelper
import dev.thib.compteurmini.components.CreateAlertDialog
import dev.thib.compteurmini.components.CreateAlertDialogLicencePlate
import dev.thib.compteurmini.components.CreateButton
import dev.thib.compteurmini.components.verifierPlaqueImmatriculation

@Composable
fun AccueilScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current

            val dbHelper = remember { PlaqueImmatriculationDatabaseHelper(context) }

            var count by remember {
                mutableStateOf(dbHelper.getNombreMini())
            }

            var showDialogAdd by remember {
                mutableStateOf(false)
            }

            var showDialogRemove by remember {
                mutableStateOf(false)
            }

            var showDialogAjouterPlaque by remember {
                mutableStateOf(false)
            }

            var textFieldValue by remember {
                mutableStateOf("")
            }

            AnimatedCounter(
                count = count,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 130.sp)
            )

            CreateButton(buttonText = "Ajouter") {
                showDialogAdd = true
            }

            if (showDialogAdd) {
                CreateAlertDialog(
                    dialogTitle = "Ajouter une Mini",
                    dialogText = "Tu as vu une mini mais tu as pas réussi à récupérer la plaque ?",
                    onDismissRequest = { showDialogAdd = false },
                    onConfirmRequest = {
                        dbHelper.ajouterUneMini()
                        count++
                        showDialogAdd = false
                    }
                )
            }

            CreateButton(buttonText = "Retirer", enabled = dbHelper.verifierNombreMiniPlaque() && count > 0) {
                showDialogRemove = true
            }

            if (showDialogRemove) {
                CreateAlertDialog(
                    dialogTitle = "Retirer une Mini",
                    dialogText = "Es-tu sûr de vouloir retirer une mini ?",
                    onDismissRequest = { showDialogRemove = false },
                    onConfirmRequest = {
                        dbHelper.retirerUneMini()
                        count--
                        showDialogRemove = false
                    }
                )
            }

            CreateButton(buttonText = "Ajouter une plaque") {
                showDialogAjouterPlaque = true
            }

            if (showDialogAjouterPlaque) {
                CreateAlertDialogLicencePlate(
                    dialogTitle = "Ajouter une plaque",
                    confirmButtonText = "Valider",
                    dismissButtonText = "Annuler",
                    textFieldValue = textFieldValue,
                    onLicensePlateChange = { newValue ->
                        textFieldValue = newValue
                    },
                    onDismissRequest = {
                        textFieldValue = ""
                        showDialogAjouterPlaque = false
                    },
                    onConfirmRequest = { licensePlate ->
                        // Ajouter la plaque à la base de données
                        dbHelper.verifierSiPlaqueExiste(PlaqueImmatriculation(plaque = licensePlate))
                        count++

                        textFieldValue = ""
                        showDialogAjouterPlaque = false
                    }
                )
            }

            /*
            CreateButton(buttonText = "Remettre tout a zéro") {
                dbHelper.remettreToutAZero()
            }
            */
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AccueilScreenPreview() {
    AccueilScreen()
}