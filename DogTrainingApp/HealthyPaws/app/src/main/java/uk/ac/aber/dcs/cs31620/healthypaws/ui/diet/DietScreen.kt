package uk.ac.aber.dcs.cs31620.healthypaws.ui.diet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.healthypaws.R
import uk.ac.aber.dcs.cs31620.healthypaws.model.Diet
import uk.ac.aber.dcs.cs31620.healthypaws.model.DietViewModel
import uk.ac.aber.dcs.cs31620.healthypaws.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.healthypaws.ui.theme.HealthyPawsTheme

@Composable
fun DietScreen(
    navController: NavHostController,
    dietViewModel: DietViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val title by remember { mutableStateOf("Meals") }
    val dietList by dietViewModel.dietList.observeAsState(listOf())

    TopLevelScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        title = title
    ){ innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            DietScreenContent(
                modifier = Modifier,
                navController = navController,
                dietList = dietList,
                dietViewModel = dietViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DietScreenContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    dietList: List<Diet> = listOf(),
    dietViewModel: DietViewModel = viewModel()
) {
    val state = rememberLazyListState()
    var addDialogIsOpen by rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        state = state,
        contentPadding = PaddingValues(bottom = 10.dp),
        modifier = Modifier
            .padding(end = 5.dp, bottom = 10.dp)
    ) {
        items(dietList) {
            Card(
                onClick = {
                },
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp, top = 5.dp)
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(Color(0xFFFFFBFE))
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = it.name,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .align(Alignment.TopStart),
                        color = Color(0xFFCC7DDA)
                    )

                    Text(
                        text = it.description,
                        fontSize = 15.sp,
                        lineHeight = 15.sp,
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 5.dp, top = 15.dp, end = 40.dp)
                            .align(Alignment.BottomStart)
                    )

                    IconButton(
                        onClick = {
                            deleteDiet(
                                diet = it,
                                doDelete = { existingDiet ->
                                    dietViewModel.deleteDiet(existingDiet)
                                }
                            )
                        },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            Icons.Filled.Remove,
                            contentDescription = stringResource(id = R.string.remove),
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                }
            }

            Divider()
        }
    }
    ConstraintLayout {

        val (addButton) = createRefs()

        FloatingActionButton(
            modifier = Modifier
                .padding(bottom = 10.dp, end = 10.dp)
                .constrainAs(addButton) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
            onClick = {
                addDialogIsOpen = true
            },
            containerColor = Color(0xFF974BA7)
        ) {
            Icon(
                Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.add_button),
            )
        }

        AddDietDialog(
            dialogIsOpen = addDialogIsOpen,
            dialogOpen = { isOpen ->
                addDialogIsOpen = isOpen
            }
        )
    }
}

private fun deleteDiet(
    diet: Diet,
    doDelete: (Diet) -> Unit = {}
) {
    if (diet.name.isNotEmpty()) {
        doDelete(diet)
    }
}

@Preview
@Composable
fun DietScreenPreview(){
    val navController = rememberNavController()
    HealthyPawsTheme {
        DietScreen(navController)
    }
}