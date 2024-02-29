package dev.thib.compteurmini

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(context: Context) {
    val navController = rememberNavController()
    Scaffold (
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        NavGraph(navController = navController)

        // Créez une instance de PlaqueImmatriculationDatabaseHelper
        val dbHelper = PlaqueImmatriculationDatabaseHelper(context)

        // Récupérer les informations de l'utilisateur depuis la base de données
        val userFromDB = dbHelper.getUserById(1) // Supposons que l'utilisateur avec l'ID 1 est l'utilisateur actuel

        // Stocker les informations de l'utilisateur dans CurrentUser
        CurrentUser.user = userFromDB
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        ScreenList.Accueil,
        ScreenList.Inventaire,
        ScreenList.Classement,
        ScreenList.Options,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: ScreenList,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        label = {
            Text(text = screen.title)
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        icon = {
            val icon = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                screen.selectedIcon
            } else {
                screen.unSelectedIcon
            }
            icon?.let {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = screen.title
                )
            } // voir pour selected / not selected
        },
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )

}