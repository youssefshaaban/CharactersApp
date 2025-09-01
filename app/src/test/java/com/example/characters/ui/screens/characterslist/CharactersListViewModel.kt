package com.example.characters.ui.screens.characterslist

import com.example.domain.entity.characters.Character
import com.example.domain.entity.characters.Location
import com.example.domain.entity.characters.PageData
import com.example.domain.usecase.GetCharactersListUseCase
import com.example.domain.util.Failure
import com.example.domain.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getCharactersListUseCase: GetCharactersListUseCase
    private lateinit var viewModel: CharactersListViewModel

    val character = Character(
        id = 1,
        created = "charactersData.created",
        episode = emptyList(),
        gender = "Male",
        image = "",
        name = "Rick Sanchez",
        species = "",
        status = "",
        type = "",
        url = "",
        location = Location("name", "url")
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getCharactersListUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCharacters success updates CharactersList and filteredCharactersList`() = runTest {
        // Given
        val fakeCharacters = listOf(
            character,
            character.copy(id = 2,name = "Luna")
        )

        coEvery { getCharactersListUseCase.invoke(1) } returns flow {
            emit(Resource.Success(PageData(page = 1, total_pages = 7, results = fakeCharacters)))
        }

        // When
        viewModel = CharactersListViewModel(getCharactersListUseCase)
        advanceUntilIdle() // flush coroutines

        // Then
        assertEquals(ListState.IDLE, viewModel.listState)
        assertEquals(2, viewModel.moviesList.size)
        assertEquals(2, viewModel.filteredMoviesList.size)
    }

    @Test
    fun `getCharacters error sets state to ERROR if CharactersList is empty`() = runTest {
        // Given
        coEvery { getCharactersListUseCase.invoke(1) } returns flow {
            emit(Resource.Error(Failure.NetworkConnection))
        }

        // When
        viewModel = CharactersListViewModel(getCharactersListUseCase)
        advanceUntilIdle()

        // Then
        assertEquals(ListState.ERROR, viewModel.listState)
        assertTrue(viewModel.moviesList.isEmpty())
    }

    @Test
    fun `onSearchQueryChanged filters Characters correctly`() = runTest {
        // Given
        val fakeCharacters = listOf(
            character,
            character.copy(id = 2,name = "Luna")
        )


        coEvery { getCharactersListUseCase.invoke(1) } returns flow {
            emit(Resource.Success(PageData(page = 1, total_pages = 7, results = fakeCharacters)))
        }

        viewModel = CharactersListViewModel(getCharactersListUseCase)
        advanceUntilIdle()

        // When - search for "Zephyr"
        viewModel.updateSearchQuery("Luna")

        // Then
        assertEquals(1, viewModel.filteredMoviesList.size)
        assertEquals("Luna", viewModel.filteredMoviesList.first().name)
    }

    @Test
    fun `updateSearchQuery with empty resets list`() = runTest {
        val fakeCharacters = listOf(
            character,
        )



        coEvery { getCharactersListUseCase.invoke(1) } returns flow {
            emit(Resource.Success(PageData(page = 1, total_pages = 7, results = fakeCharacters)))
        }

        viewModel = CharactersListViewModel(getCharactersListUseCase)
        advanceUntilIdle()

        // When - search something invalid then reset
        viewModel.updateSearchQuery("Invalid")
        assertTrue(viewModel.filteredMoviesList.isEmpty())

        viewModel.updateSearchQuery("")
        assertEquals(1, viewModel.filteredMoviesList.size)
    }
}
