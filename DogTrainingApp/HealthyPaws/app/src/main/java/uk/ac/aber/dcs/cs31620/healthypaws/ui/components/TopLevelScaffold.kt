package uk.ac.aber.dcs.cs31620.healthypaws.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopLevelScaffold(
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    title: String,
    pageContent: @Composable (innerPadding: PaddingValues) -> Unit = { },
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    Scaffold(
        topBar = {
            if(navController.currentDestination?.route != "training" && navController.currentDestination?.route != "exercises") {
                MainPageTopAppBar(
                    title = title,
                    onBackPressed = {
                    })
            }
        },
        bottomBar = {
            MainPageNavigationBar(navController)
        },
        content = { innerPadding ->
            pageContent(innerPadding)
        }
    )
}