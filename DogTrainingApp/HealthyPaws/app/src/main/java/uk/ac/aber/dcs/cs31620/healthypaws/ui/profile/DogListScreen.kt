package uk.ac.aber.dcs.cs31620.healthypaws.ui.profile

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import uk.ac.aber.dcs.cs31620.healthypaws.R
import uk.ac.aber.dcs.cs31620.healthypaws.model.Dog
import uk.ac.aber.dcs.cs31620.healthypaws.model.DogViewModel
import uk.ac.aber.dcs.cs31620.healthypaws.model.ExercisesViewModel
import uk.ac.aber.dcs.cs31620.healthypaws.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.healthypaws.ui.navigation.Screen

@Composable
fun DogListScreen(
    navController: NavHostController,
    dogViewModel: DogViewModel = viewModel(),
    exercisesViewModel: ExercisesViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val title by remember { mutableStateOf("Dogs") }
    val dogList by dogViewModel.dogList.observeAsState(listOf())

    TopLevelScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        title = title,
    ){ innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            DogListScreenContent(
                modifier = Modifier,
                navController = navController,
                dogList = dogList,
                dogViewModel = dogViewModel,
                exercisesViewModel = exercisesViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
private fun DogListScreenContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    dogList: List<Dog> = listOf(),
    dogViewModel: DogViewModel = viewModel(),
    exercisesViewModel: ExercisesViewModel = viewModel()
) {
    val state = rememberLazyListState()
    val context = LocalContext.current

    LazyColumn(
        state = state,
        contentPadding = PaddingValues(bottom = 100.dp),
        modifier = Modifier
            .padding(10.dp)
    ) {
        items(dogList) {
            Card(
                modifier = Modifier
                    .height(140.dp)
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                    .fillMaxSize()
                    .clickable {
                        if (navController.previousBackStackEntry?.destination?.route == "exercise step three") {
                            it.addExercise(exercisesViewModel.selectedExercise.value!!)
                            navController.navigate(Screen.Exercises.route)
                        } else {
                            dogViewModel.numOfDog = dogList.indexOf(it)
                            dogViewModel.currentListOfKnownCommands = it.knownCommands.toMutableList()
                            navController.navigate(Screen.Profile.route)
                        }
                    },
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(130.dp)
                            .padding(start = 10.dp)
                    ) {
                        GlideImage(
                            model = Uri.parse(it.imagePath),
                            contentDescription = stringResource(id = R.string.dog_profile_image),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .size(110.dp)
                                .clip(CircleShape)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(220.dp)
                            .align(Alignment.CenterEnd)
                    ) {
                        Text(
                            text = it.name,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                        )
                    }
                }
            }
        }
    }
}

