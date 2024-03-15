package uk.ac.aber.dcs.cs31620.healthypaws.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPageTopAppBar(
    onBackPressed: () -> Unit = {},
    title: String,
){

    val navController = rememberNavController()

    CenterAlignedTopAppBar(
        title = { Text(
            title,
        fontSize = 30.sp) },
    )
}