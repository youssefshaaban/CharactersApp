package com.example.characters.ui.screens.characters_detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.domain.entity.characters.Location
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharacterDetailScreenTest {
    val character = com.example.domain.entity.characters.Character(
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

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun characterDetailContent_displaysCharacterInfo() {

        composeTestRule.setContent {
            CharacterDetailContent(
                character = character,
                onBackClick = {}
            )
        }

        // Assertions
        composeTestRule.onNodeWithText("Rick Sanchez").assertIsDisplayed()
        composeTestRule.onNodeWithText("Human • Male").assertIsDisplayed()
        composeTestRule.onNodeWithText("Alive").assertIsDisplayed()
        composeTestRule.onNodeWithText("Location : ").assertIsDisplayed()
        composeTestRule.onNodeWithText("Earth").assertIsDisplayed()
    }

    @Test
    fun backButton_click_triggersCallback() {
        var clicked = false

        composeTestRule.setContent {
            CharacterDetailContent(
                character = character,
                onBackClick = { clicked = true }
            )
        }

        // Click back button
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        // Verify callback
        assert(clicked)
    }
}