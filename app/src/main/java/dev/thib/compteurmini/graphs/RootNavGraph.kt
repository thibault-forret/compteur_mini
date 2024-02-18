package dev.thib.compteurmini.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.thib.compteurmini.screens.accueil.AccueilScreen

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.ACCUEIL
    ) {
        //authNavGraph(navController = navController)
        composable(route = Graph.ACCUEIL) {
            AccueilScreen()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val ACCUEIL = "accueil_graph"
    const val INVENTAIRE = "inventaire_graph"
    const val CLASSEMENT = "classement_graph"
    const val OPTIONS = "options_graph"
}