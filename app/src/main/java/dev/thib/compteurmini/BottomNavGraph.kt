package dev.thib.compteurmini

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.thib.compteurmini.screens.accueil.AccueilScreen
import dev.thib.compteurmini.screens.ClassementScreen
import dev.thib.compteurmini.screens.InventaireScreen
import dev.thib.compteurmini.screens.OptionsScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Accueil.route
    ) {
        composable(route = BottomBarScreen.Accueil.route) {
            AccueilScreen()
        }
        composable(route = BottomBarScreen.Inventaire.route) {
            InventaireScreen()
        }
        composable(route = BottomBarScreen.Classement.route) {
            ClassementScreen()
        }
        composable(route = BottomBarScreen.Options.route) {
            OptionsScreen()
        }
    }
}