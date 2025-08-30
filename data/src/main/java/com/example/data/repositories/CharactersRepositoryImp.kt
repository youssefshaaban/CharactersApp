package com.example.data.repositories

import com.example.data.model.characters_list.CharactersResponse
import com.example.data.model.characters_list.Info
import com.example.data.remote.CharactersAPI
import com.example.data.utils.apiCall
import com.example.domain.entity.QueryCharacters
import com.example.domain.entity.characters.Character
import com.example.domain.entity.characters.Location
import com.example.domain.entity.characters.PageData
import com.example.domain.mapper.DataMapper
import com.example.domain.repositories.ICharactersRepository
import com.example.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class CharactersRepositoryImp @Inject constructor(
    private val charactersAPI: CharactersAPI,
    private val dataMapperMovieRemote: DataMapper<CharactersResponse, PageData>,
) : ICharactersRepository {

    override suspend fun getCharactersList(queryCharacters: QueryCharacters): Flow<Resource<PageData>> {
        return flow {
            val response =
                apiCall { charactersAPI.getCharacters(queryCharacters.toQueryMap()) }
            when (response) {
                is Resource.Success -> {
                    val currentData = response.data
                    emit(
                        Resource.Success(
                            dataMapperMovieRemote.execute(
                                response.data.copy(
                                    info = currentData.info.copy(
                                        currentPage = queryCharacters.page
                                    )
                                )
                            )
                        )
                    )
                }

                is Resource.Error -> {
                    emit(Resource.Error(response.error))
                }
            }
        }
    }

    override suspend fun getCharacterById(characterId: Int): Flow<Resource<Character>> {
        return flow {
            val response = apiCall { charactersAPI.getCharacterById(characterId) }
            when (response) {
                is Resource.Success -> {
                    val currentData = response.data
                    emit(Resource.Success(Character(name = currentData.name, id = currentData.id, location = Location(currentData.location.name,currentData.location.url), url = currentData.url, type = currentData.type, status = currentData.status, species = currentData.species, image = currentData.image, gender = currentData.gender, episode = currentData.episode, created = currentData.created)))
                }

                is Resource.Error -> {
                    emit(Resource.Error(response.error))
                }
            }
        }

    }


}