package com.example.characters.ui.screens.characterslist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.characters.Character
import com.example.domain.usecase.GetCharactersListUseCase
import com.example.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersListViewModel @Inject constructor(private val getCharactersListUseCase: GetCharactersListUseCase) :
    ViewModel() {
    private var page = 1
    private var canPaginate = false
    private val MANPAGE = 500
    var listState by mutableStateOf(ListState.IDLE)
    val moviesList = mutableListOf<Character>()
    private val _searchQueryFlow = MutableStateFlow("")
    val searchQueryFlow: StateFlow<String> = _searchQueryFlow.asStateFlow()
    private val _filteredMoviesList = mutableStateListOf<Character>()
    val filteredMoviesList: List<Character>
        get() = _filteredMoviesList

    init {
        getCharacters()
        viewModelScope.launch {
            _searchQueryFlow
                .debounce(2000) // Wait for 3 seconds
                .distinctUntilChanged()
                .collect { query ->
                    updateSearchQuery(query)
                }
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _searchQueryFlow.value = newQuery
    }

    fun getCharacters() = viewModelScope.launch {
        if (page == 1 || canPaginate) {
            listState = if (page == 1) ListState.LOADING else ListState.PAGINATING
            getCharactersListUseCase(page).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        canPaginate = page <= MANPAGE
                        if (page == result.data.page) {
                            if (page == 1) {
                                moviesList.clear()
                                _filteredMoviesList.clear()
                                moviesList.addAll(result.data.results)
                                _filteredMoviesList.addAll(result.data.results)
                            } else {
                                moviesList.addAll(result.data.results)
                                _filteredMoviesList.addAll(result.data.results)
                            }
                            if (canPaginate) {
                                page++
                            }

                        }
                        listState = ListState.IDLE
                    }

                    is Resource.Error -> {
                        listState = if (moviesList.isEmpty()) ListState.ERROR
                        else ListState.IDLE
                    }
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        if (query.isEmpty()) {
            canPaginate = page <= MANPAGE
            _filteredMoviesList.clear()
            _filteredMoviesList.addAll(moviesList)
        } else {
            canPaginate = false
            filterMovies(query)
        }

    }

    // Filter movies based on the search query
    private fun filterMovies(queryString: String) {
        _filteredMoviesList.clear()
        _filteredMoviesList.addAll(moviesList.filter { movie ->
            movie.name.contains(queryString, ignoreCase = true)
        })
    }

}