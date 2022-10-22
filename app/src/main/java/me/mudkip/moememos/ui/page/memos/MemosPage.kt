package me.mudkip.moememos.ui.page.memos

import androidx.activity.compose.BackHandler
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import me.mudkip.moememos.ui.component.MemosList
import me.mudkip.moememos.ui.component.SideDrawer
import me.mudkip.moememos.ui.page.common.RouteName
import me.mudkip.moememos.viewmodel.MemosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemosPage(
    navController: NavHostController,
    viewModel: MemosViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    BackHandler(enabled = drawerState.isOpen) {
        scope.launch {
            drawerState.close()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SideDrawer(
                navController = navController,
                memosViewModel = viewModel
            )
        }
    ) {
        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                LargeTopAppBar(
                    title = { Text(text = "Memos") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = {

                        }) {
                            Icon(Icons.Filled.Search, contentDescription = "Search")
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },

            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = {
                        navController.navigate(RouteName.INPUT)
                    },
                    text = { Text("New Memo") },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "Compose") }
                )
            },

            content = { innerPadding ->
                MemosList(
                    contentPadding = innerPadding,
                    viewModel = viewModel
                )
            }
        )
    }
}