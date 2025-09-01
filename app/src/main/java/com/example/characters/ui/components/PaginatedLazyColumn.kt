package com.example.characters.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.characters.R
import com.example.characters.ui.screens.characterslist.ListState
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter


@Composable
fun <T> PaginatedLazyColumn(
    items: PersistentList<T>,  // Using PersistentList for efficient state management
    loadMoreItems: () -> Unit,  // Function to load more items
    lazyGridState: LazyListState,  // Track the scroll state of the LazyColumn
    buffer: Int = 2,  // Buffer to load more items when we get near the end
    listState: ListState,
    modifier: Modifier = Modifier,
    keyExtractor: (T) -> Any, // extracts a stable ID from item
    listContent: @Composable (T, Int) -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            // Get the total number of items in the list
            val totalItemsCount = lazyGridState.layoutInfo.totalItemsCount
            // Get the index of the last visible item
            val lastVisibleItemIndex =
                lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            // Check if we have scrolled near the end of the list and more items should be loaded
            lastVisibleItemIndex >= (totalItemsCount - buffer) && listState!=ListState.PAGINATING
        }
    }
    LaunchedEffect(lazyGridState) {
        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged()
            .filter { it }  // Ensure that we load more items only when needed
            .collect {
                Log.e("call", "load more")
                loadMoreItems()
            }
    }
    // LazyColumn to display the list of items
    if (listState==ListState.LOADING) {
        Loading(modifier = Modifier.fillMaxSize().testTag("loading"))
    }
    if (listState==ListState.ERROR){
        Column (modifier=Modifier.fillMaxSize().testTag("errorContent"), horizontalAlignment = Alignment.CenterHorizontally , verticalArrangement = Arrangement.Center) {
            Text(text = stringResource(R.string.something_want_wrong))
        }
    }
    LazyColumn (
        modifier = modifier
            .fillMaxSize(),// Add padding for better visual spacing
        state = lazyGridState,
    ) {
        // Render each item in the list using a unique key
        itemsIndexed(items, key = { _, item -> keyExtractor(item)}){
            index, item ->
            listContent(item,index)
        }
//        itemsIndexed(items, key = { _, item -> keyExtractor(item) }) { index, item ->
//            listContent(item)
//        }
        item(
            key = listState,
        ) {
            if (listState==ListState.PAGINATING) {
                PaginationLoading()
            }

        }

    }

}