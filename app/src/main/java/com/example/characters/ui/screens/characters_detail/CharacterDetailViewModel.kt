package com.example.characters.ui.screens.characters_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.characters.Character
import com.example.domain.usecase.GetCharacterByIdUseCase
import com.example.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(private val getCharacterByIdUseCase: GetCharacterByIdUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow(MovieDetailState())
    val state: StateFlow<MovieDetailState> = _state.asStateFlow()
    fun getCharacterDetail(characterId: String) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getCharacterByIdUseCase.invoke(id = characterId.toInt()).collectLatest { result->
                when(result){
                    is Resource.Success->{
                        _state.update { it.copy(character = result.data, isLoading = false) }
                    }
                    is Resource.Error->{
                        _state.update { it.copy(isError = true, isLoading = false) }
                    }
                }
            }
        }
    }
}

data class MovieDetailState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
   val character: Character? = null
)