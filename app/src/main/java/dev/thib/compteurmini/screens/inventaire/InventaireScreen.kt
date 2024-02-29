package dev.thib.compteurmini.screens.inventaire

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.thib.compteurmini.PlaqueImmatriculation
import dev.thib.compteurmini.PlaqueImmatriculationDatabaseHelper

@Composable
fun InventaireScreen() {
    // Clé pour forcer la recomposition de InventaireScreen
    var key by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        val context = LocalContext.current

        val dbHelper = remember { PlaqueImmatriculationDatabaseHelper(context) }
        val plaques = remember { dbHelper.obtenirPlaques() }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            val nombrePlaque = dbHelper.getNombrePlaque()
            Text(
                text = "Nombre total de plaques : $nombrePlaque",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (plaques.isNotEmpty()) {
                for (plaque in plaques) {
                    PlaqueItem(plaque = plaque, dbHelper = dbHelper)
                }
            } else {
                Text(
                    text = "Aucune plaque trouvée.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
@Composable
fun PlaqueItem(
    plaque: PlaqueImmatriculationDatabaseHelper.PlaqueInfo,
    dbHelper: PlaqueImmatriculationDatabaseHelper
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "Plaque : ${plaque.plaque}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Nombre de fois vu : ${plaque.nombreFoisVu}",
            style = MaterialTheme.typography.bodyLarge
        )

        if (plaque.nombreFoisVu > 1) {
            Button(
                onClick = {
                    dbHelper.diminuerNombreFoisVu(plaque.id)
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp)
            ) {
                Text("Diminuer le nombre de fois vu")
            }
        } else {
            Button(
                onClick = {
                    dbHelper.supprimerPlaque(plaque.id)
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp)
            ) {
                Text("Supprimer la plaque")
            }
        }
    }
}