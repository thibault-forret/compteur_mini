package dev.thib.compteurmini

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.thib.compteurmini.screens.AccueilScreen
import dev.thib.compteurmini.screens.ClassementScreen
import dev.thib.compteurmini.screens.inventaire.InventaireScreen
import dev.thib.compteurmini.screens.OptionsScreen
import dev.thib.compteurmini.screens.inventaire.AjouterPlaqueScreen
import dev.thib.compteurmini.screens.inventaire.DetailPlaqueScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ScreenList.Accueil.route
    ) {
        // ------------ ACCUEIL
        composable(route = ScreenList.Accueil.route) {
            AccueilScreen()
        }
        // ------------ INVENTAIRE
        composable(route = ScreenList.Inventaire.route) {
            InventaireScreen()
        }
        // ------------ DETAIL PLAQUE
        composable(route = ScreenList.DetailPlaque.route) {
            DetailPlaqueScreen()
        }
        // ------------ AJOUTER PLAQUE
        composable(route = ScreenList.AjouterPlaque.route) {
            AjouterPlaqueScreen()
        }
        // ------------ CLASSEMENT
        composable(route = ScreenList.Classement.route) {
            ClassementScreen()
        }
        // ------------ OPTIONS
        composable(route = ScreenList.Options.route) {
            OptionsScreen()
        }
    }
}