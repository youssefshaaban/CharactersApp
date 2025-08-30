package com.example.domain.repositories

import com.example.domain.entity.QueryCharacters
import com.example.domain.entity.characters.Character
import com.example.domain.entity.characters.PageData
import com.example.domain.util.Resource
import kotlinx.coroutines.flow.Flow


interface ICharactersRepository {
    suspend fun getCharactersList(queryCharacters: QueryCharacters):Flow<Resource<PageData>>

    suspend fun getCharacterById(characterId: Int):Flow<Resource<Character>>
}