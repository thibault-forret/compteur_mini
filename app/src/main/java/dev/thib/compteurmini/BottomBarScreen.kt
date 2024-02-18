package dev.thib.compteurmini

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen (
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector
) {
    object Accueil: BottomBarScreen(
        route = "accueil",
        title = "Acceuil",
        selectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Outlined.Home
    )
    object Inventaire: BottomBarScreen(
        route = "inventaire",
        title = "Inventaire",
        selectedIcon = Icons.Filled.Person,
        unSelectedIcon = Icons.Outlined.Person
    )
    object Classement: BottomBarScreen(
        route = "classement",
        title = "Classement",
        selectedIcon = Icons.Filled.Star,
        unSelectedIcon = Icons.Outlined.Star
    )
    object Options: BottomBarScreen(
        route = "options",
        title = "Options",
        selectedIcon = Icons.Filled.Settings,
        unSelectedIcon = Icons.Outlined.Settings
    )
}