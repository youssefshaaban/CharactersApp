package com.example.characters.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.characters.ui.screens.characters_detail.CharacterDetailScreen
import com.example.characters.ui.screens.characterslist.CharactersListScreen


@Composable
fun NavGraph(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavRoute.MoviesList
    ) {
        addCharacterListScreen(navController, this)

        addCharacterDetailScreen(navController, this)
    }
}

fun addCharacterDetailScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable<NavRoute.CharacterDetail> { backStackEntry ->
        val profile: NavRoute.CharacterDetail = backStackEntry.toRoute()
        CharacterDetailScreen (profile.characterId){
            navController.popBackStack()
        }
    }
}

fun addCharacterListScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable<NavRoute.MoviesList> {
        CharactersListScreen { movie ->
            navController.navigate(NavRoute.CharacterDetail(movie.id.toString()))
        }
    }

}
