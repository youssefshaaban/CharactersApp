package com.example.data.repositories

import com.example.data.model.characters_list.CharactersResponse
import com.example.data.remote.CharactersAPI
import com.example.data.util.MainCoroutineRuleTest
import com.example.data.util.TestUtil
import com.example.domain.entity.QueryCharacters
import com.example.domain.entity.characters.PageData
import com.example.domain.mapper.DataMapper
import com.example.domain.util.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MovieRepositoryImpTest {

    @get:Rule
    val coroutineRule = MainCoroutineRuleTest() // Custom JUnit Rule to control coroutines

    private val charactersAPI: CharactersAPI = mockk()
    private val dataMapper: DataMapper<CharactersResponse, PageData> = mockk()


    private lateinit var repository: CharactersRepositoryImp

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Before
    fun setUp() {
        repository = CharactersRepositoryImp(
            charactersAPI,
            dataMapper,

        )
    }



    @Test
    fun `getCharacters from API return success`() =
        runTest {
            // Given
            val queryCharacters = QueryCharacters(page = 1)
            val apiResponse = TestUtil.createMockGetCharactersResponse()
            val pageData = PageData(
                page = 1,
                results = listOf(TestUtil.createCharacter()),
                total_pages = 10
            )


            coEvery { charactersAPI.getCharacters(any()) } returns apiResponse
            coEvery { dataMapper.execute(any()) } returns pageData

            // When
            val result = repository.getCharactersList(queryCharacters).first()

            // Then
            assertEquals(Resource.Success(pageData), result)
            coVerify { charactersAPI.getCharacters(any()) }
        }

    @Test
    fun `getCharacters on_API error returns error`() = runTest {
        val queryCharacters = QueryCharacters(page = 1)

        coEvery { charactersAPI.getCharacters(any()) } throws RuntimeException()

        val result =
            repository.getCharactersList(queryCharacters).first()
        assert(result is Resource.Error)
        coVerify { charactersAPI.getCharacters(queryCharacters.toQueryMap()) }
    }



}