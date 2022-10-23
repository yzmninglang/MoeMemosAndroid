package me.mudkip.moememos.ui.page.memos

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import me.mudkip.moememos.ui.component.MemosCard
import me.mudkip.moememos.viewmodel.ArchivedMemoListViewModel
import me.mudkip.moememos.viewmodel.LocalArchivedMemos

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ArchivedMemoList(
    viewModel: ArchivedMemoListViewModel = hiltViewModel(),
    contentPadding: PaddingValues
) {
    CompositionLocalProvider(LocalArchivedMemos provides viewModel) {
        LazyColumn(
            modifier = Modifier.consumedWindowInsets(contentPadding),
            contentPadding = contentPadding
        ) {
            items(viewModel.memos, key = { it.id }) { memo ->
                MemosCard(memo)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadMemos()
    }
}