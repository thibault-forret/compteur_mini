package dev.thib.compteurmini

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

sealed class ScreenList(
    val route: String,
    val title: String,
    val selectedIcon: Int? = null,
    val unSelectedIcon: Int? = null
) {
    object Accueil : ScreenList(
        route = "accueil",
        title = "Accueil",
        selectedIcon = R.drawable.ic_home_filled,
        unSelectedIcon = R.drawable.ic_home_outlined
    )

    object Inventaire : ScreenList(
        route = "inventaire",
        title = "Inventaire",
        selectedIcon = R.drawable.ic_dossier_filled,
        unSelectedIcon = R.drawable.ic_dossier_outlined
    )

    object AjouterPlaque : ScreenList(
        route = "ajouter_plaque",
        title = "Ajouter Plaque",
    )

    object DetailPlaque : ScreenList(
        route = "detail_plaque",
        title = "Détail Plaque",
    )

    object Classement : ScreenList(
        route = "classement",
        title = "Classement",
        selectedIcon = R.drawable.ic_trophee_filled,
        unSelectedIcon = R.drawable.ic_trophee_outlined//ImageVector.vectorResource(R.drawable.outlined_dossier)
    )

    object Options : ScreenList(
        route = "options",
        title = "Options",
        selectedIcon = R.drawable.ic_options_filled,
        unSelectedIcon = R.drawable.ic_options_outlined
    )
}