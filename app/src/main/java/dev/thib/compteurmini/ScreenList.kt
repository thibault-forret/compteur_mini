package dev.thib.compteurmini

sealed class ScreenList(
    val route: String,
    val title: String,
    val selectedIcon: Int? = null,
    val unSelectedIcon: Int? = null
) {
    data object Accueil : ScreenList(
        route = "accueil",
        title = "Accueil",
        selectedIcon = R.drawable.ic_home_filled,
        unSelectedIcon = R.drawable.ic_home_outlined
    )

    data object Inventaire : ScreenList(
        route = "inventaire",
        title = "Inventaire",
        selectedIcon = R.drawable.ic_dossier_filled,
        unSelectedIcon = R.drawable.ic_dossier_outlined
    )

    data object AjouterPlaque : ScreenList(
        route = "ajouter_plaque",
        title = "Ajouter Plaque",
    )

    data object DetailPlaque : ScreenList(
        route = "detail_plaque",
        title = "Détail Plaque",
    )

    data object Classement : ScreenList(
        route = "classement",
        title = "Classement",
        selectedIcon = R.drawable.ic_trophee_filled,
        unSelectedIcon = R.drawable.ic_trophee_outlined
    )

    data object Options : ScreenList(
        route = "options",
        title = "Options",
        selectedIcon = R.drawable.ic_options_filled,
        unSelectedIcon = R.drawable.ic_options_outlined
    )
}