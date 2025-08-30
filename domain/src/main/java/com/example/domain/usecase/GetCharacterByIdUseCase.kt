package com.example.domain.usecase


import com.example.domain.entity.characters.Character
import com.example.domain.repositories.ICharactersRepository
import com.example.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetCharacterByIdUseCase @Inject constructor(private val iCharactersRepository: ICharactersRepository) {
     suspend operator fun invoke(id:Int): Flow<Resource<Character>> {
        return iCharactersRepository.getCharacterById(id)
    }
}