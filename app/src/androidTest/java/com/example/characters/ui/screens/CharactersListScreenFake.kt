package com.example.characters.ui.screens

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import com.example.characters.ui.components.PaginatedLazyColumn
import com.example.characters.ui.screens.characterslist.CharacterItem
import com.example.characters.ui.screens.characterslist.ListState
import kotlinx.collections.immutable.toPersistentList

@Composable
fun CharactersListScreenFake(
    characters: List<com.example.domain.entity.characters.Character>,
    listState: ListState = ListState.IDLE,
    onCharacterClick: (com.example.domain.entity.characters.Character) -> Unit = {},
    onLoadMore: () -> Unit = {}
) {
    val lazyListState = rememberLazyListState()

    PaginatedLazyColumn(
        items = characters.toPersistentList(),
        loadMoreItems = onLoadMore,
        lazyGridState = lazyListState,
        listState = listState,
        keyExtractor = { it.id }
    ) { character,index ->
        CharacterItem(index= index, character = character, onClick = onCharacterClick)
    }
}
