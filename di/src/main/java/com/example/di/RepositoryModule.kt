package com.example.di

import com.example.di.qulifier.CharactersDataMapper
import com.example.data.model.characters_list.CharactersResponse
import com.example.data.remote.CharactersAPI
import com.example.data.repositories.CharactersRepositoryImp
import com.example.domain.entity.characters.PageData
import com.example.domain.mapper.DataMapper
import com.example.domain.repositories.ICharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCharactersRepository(
        charactersAPI: CharactersAPI,
        @CharactersDataMapper dataMapper: DataMapper<CharactersResponse, PageData>,
    ): ICharactersRepository {
        return CharactersRepositoryImp(
            charactersAPI,
            dataMapper,
        )
    }


}