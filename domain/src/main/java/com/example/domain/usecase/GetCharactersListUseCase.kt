package com.example.domain.usecase

import com.example.domain.entity.QueryCharacters
import com.example.domain.entity.characters.PageData
import com.example.domain.repositories.ICharactersRepository
import com.example.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetCharactersListUseCase @Inject constructor(private val iCharactersRepository: ICharactersRepository) {
     suspend operator fun invoke(page:Int): Flow<Resource<PageData>> {
        return iCharactersRepository.getCharactersList(QueryCharacters(page=page))
    }
}