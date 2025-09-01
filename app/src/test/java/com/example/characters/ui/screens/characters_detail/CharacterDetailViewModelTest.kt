package com.example.characters.ui.screens.characters_detail

import com.example.domain.entity.characters.Character
import com.example.domain.entity.characters.Location
import com.example.domain.usecase.GetCharacterByIdUseCase
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
class CharacterDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getCharacterByIdUseCase: GetCharacterByIdUseCase
    private lateinit var viewModel: CharacterDetailViewModel

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
        getCharacterByIdUseCase = mockk()
        viewModel = CharacterDetailViewModel(getCharacterByIdUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCharacterDetail success updates state with character`() = runTest {
        // Given

        coEvery { getCharacterByIdUseCase.invoke(1) } returns flow {
            emit(Resource.Success(character))
        }

        // When
        viewModel.getCharacterDetail("1")
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertFalse(state.isError)
        assertEquals(character, state.character)
    }

    @Test
    fun `getCharacterDetail error updates state with error`() = runTest {
        // Given
        coEvery { getCharacterByIdUseCase.invoke(1) } returns flow {
            emit(Resource.Error(Failure.NetworkConnection))
        }

        // When
        viewModel.getCharacterDetail("1")
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertTrue(state.isError)
        assertNull(state.character)
    }

    @Test
    fun `getCharacterDetail sets loading before result`() = runTest {
        // Given

        coEvery { getCharacterByIdUseCase.invoke(1) } returns flow {
            emit(Resource.Success(character))
        }

        // When
        viewModel.getCharacterDetail("1")

        // Immediately after calling, before advanceUntilIdle
        val loadingState = viewModel.state.value
        assertTrue(loadingState.isLoading)

        // After coroutine completes
        advanceUntilIdle()
        val finalState = viewModel.state.value
        assertFalse(finalState.isLoading)
    }
}
