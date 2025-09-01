package com.example.characters.ui.screens.characterslist

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.characters.ui.screens.CharactersListScreenFake
import com.example.domain.entity.characters.Character
import com.example.domain.entity.characters.Location
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class CharactersListScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()
    val character = Character(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        gender = "Male",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        location = Location("Earth", ""),
        episode = emptyList(),
        created = "",
        type = "",
        url = ""
    )
    @Test
    fun displaysCharactersInList() {
        val characters = listOf(
            character,
            character.copy(id = 2, name = "Morty Smith"))
        composeRule.setContent {
            CharactersListScreenFake(characters = characters)
        }
        composeRule.onNodeWithText("Rick Sanchez").assertIsDisplayed()
        composeRule.onNodeWithText("Morty Smith").assertIsDisplayed()
    }

    @Test
    fun showsLoading_whenListStateIsLoading() {
        composeRule.setContent {
            CharactersListScreenFake(
                characters = emptyList(),
                listState = ListState.LOADING
            )
        }

        // If your Loading() has no text, give it a testTag in PaginatedLazyColumn
        composeRule.onNodeWithTag("loading").assertExists()
    }

    @Test
    fun showsError_whenListStateIsError() {
        composeRule.setContent {
            CharactersListScreenFake(
                characters = emptyList(),
                listState = ListState.ERROR
            )
        }

        composeRule.onNodeWithTag("errorContent").assertExists()
    }

}
