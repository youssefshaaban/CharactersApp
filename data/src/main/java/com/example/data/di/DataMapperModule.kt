package com.example.data.di

import com.example.data.di.qulifier.CharactersDataMapper
import com.example.data.mapper.CharactersPageDataMapper
import com.example.data.model.characters_list.CharactersResponse
import com.example.domain.entity.characters.PageData
import com.example.domain.mapper.DataMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @CharactersDataMapper
    fun provideCharactersPageDataMapper(): DataMapper<CharactersResponse, PageData> {
        return CharactersPageDataMapper()
    }


}