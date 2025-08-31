package com.example.characters.ui.navigation

import kotlinx.serialization.Serializable


sealed class NavRoute {

    @Serializable
    data object MoviesList: NavRoute()

    @Serializable
    data class CharacterDetail(val characterId: String): NavRoute()

}

