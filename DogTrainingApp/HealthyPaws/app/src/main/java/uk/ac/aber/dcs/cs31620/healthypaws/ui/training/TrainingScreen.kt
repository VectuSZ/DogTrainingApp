package uk.ac.aber.dcs.cs31620.healthypaws.ui.training

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdsClick
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import uk.ac.aber.dcs.cs31620.healthypaws.R
import uk.ac.aber.dcs.cs31620.healthypaws.model.Difficulty
import uk.ac.aber.dcs.cs31620.healthypaws.model.Exercise
import uk.ac.aber.dcs.cs31620.healthypaws.model.ExercisesViewModel
import uk.ac.aber.dcs.cs31620.healthypaws.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.healthypaws.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.healthypaws.ui.theme.HealthyPawsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingScreen(
    navController: NavHostController,
    exercisesViewModel: ExercisesViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val title by remember { mutableStateOf("") }
    val exerciseList by exercisesViewModel.exerciseList.observeAsState(listOf())

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
            TrainingScreenContent(
                modifier = Modifier,
                navController = navController,
                exercisesList = exerciseList,
                exercisesViewModel = exercisesViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
private fun TrainingScreenContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    exercisesList: List<Exercise> = listOf(),
    exercisesViewModel: ExercisesViewModel = viewModel()
) {

    val state = rememberLazyListState()

    if (exercisesList.isNotEmpty()) {
        val specifiedExerciseList: List<Exercise> =
            listOf(exercisesList[0], exercisesList[10], exercisesList[14])

        ConstraintLayout {

            val (trainingCard, exerciseCard, clickerCard, suggestedExercisesText, showAllButton, exercisesColumn) = createRefs()

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(top = 10.dp, bottom = 10.dp)
                    .constrainAs(trainingCard) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    },
                colors = CardDefaults.cardColors(Color(0xFFFFFBFE))
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = stringResource(id = R.string.training),
                        fontSize = 30.sp,
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .align(Alignment.CenterStart)
                    )
                }
            }

            Card(
                onClick = {
                    navController.navigate(Screen.Exercises.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .constrainAs(exerciseCard) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(trainingCard.bottom)
                    },
                colors = CardDefaults.cardColors(Color(0xFFFFFBFE))
            ) {
                Box(modifier = Modifier.fillMaxSize()) {

                    Divider(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                    )

                    Icon(
                        Icons.Filled.Pets,
                        contentDescription = stringResource(id = R.string.forward),
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .size(30.dp)
                            .align(Alignment.CenterStart),
                        tint = Color(0xFF974BA7)
                    )

                    Text(
                        text = stringResource(id = R.string.exercises),
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 60.dp)
                            .align(Alignment.CenterStart)
                    )

                    IconButton(
                        onClick = {
                            navController.navigate(Screen.Exercises.route)
                        },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            Icons.Filled.ArrowForwardIos,
                            contentDescription = stringResource(id = R.string.forward),
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }

                    Divider(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                    )
                }
            }


            Card(
                onClick = {
                    navController.navigate(Screen.Clicker.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .constrainAs(clickerCard) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(exerciseCard.bottom)
                    },
                colors = CardDefaults.cardColors(Color(0xFFFFFBFE))
            ) {
                Box(modifier = Modifier.fillMaxSize()) {

                    Icon(
                        Icons.Filled.AdsClick,
                        contentDescription = stringResource(id = R.string.forward),
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .size(30.dp)
                            .align(Alignment.CenterStart),
                        tint = Color(0xFF974BA7)

                    )

                    Text(
                        text = stringResource(id = R.string.clicker),
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 60.dp)
                            .align(Alignment.CenterStart)
                    )

                    IconButton(
                        onClick = {
                            navController.navigate(Screen.Clicker.route)
                        },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            Icons.Filled.ArrowForwardIos,
                            contentDescription = stringResource(id = R.string.forward),
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }

                    Divider(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                    )
                }
            }

            Text(
                text = stringResource(id = R.string.suggested_exercises),
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(start = 20.dp, top = 15.dp)
                    .constrainAs(suggestedExercisesText) {
                        start.linkTo(parent.start)
                        top.linkTo(clickerCard.bottom)
                    }

            )

            TextButton(
                onClick = {
                    navController.navigate(Screen.Exercises.route)
                },
                modifier = Modifier
                    .padding(start = 35.dp, top = 8.dp)
                    .constrainAs(showAllButton) {
                        start.linkTo(suggestedExercisesText.end)
                        top.linkTo(clickerCard.bottom)
                        end.linkTo(parent.end)
                    }
            ) {
                Text(
                    text = stringResource(id = R.string.show_all_button),
                    fontSize = 19.sp,
                    color = Color(0xFF974BA7)
                )
            }

            LazyColumn(
                state = state,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp, top = 25.dp)
                    .constrainAs(exercisesColumn) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(suggestedExercisesText.bottom)
                    }
            ) {
                items(specifiedExerciseList) {
                    Card(
                        modifier = Modifier
                            .height(140.dp)
                            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                            .fillMaxSize()
                            .clickable {
                                exercisesViewModel.selectedExercise.value = it
                                navController.navigate(Screen.ExerciseDetails.route)
                            },
                        colors = CardDefaults.cardColors(Color(0xFFFFFBFE))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(170.dp)
                                    .padding(start = 10.dp)
                            ) {
                                GlideImage(
                                    model = Uri.parse(it.imagePath),
                                    contentDescription = stringResource(id = R.string.dog_exercise_image),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                        .size(130.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF8C8C8C))
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(180.dp)
                                    .align(Alignment.CenterEnd)
                            ) {
                                Text(
                                    text = it.name,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .padding(top = 40.dp)
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.BottomStart)
                                        .padding(bottom = 35.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = stringResource(id = R.string.difficulty),
                                        fontWeight = FontWeight.Light,
                                        fontSize = 18.sp
                                    )

                                    if(it.difficulty == Difficulty.EASY) {
                                        repeat(1) {
                                            Icon(
                                                Icons.Filled.Pets,
                                                contentDescription = stringResource(id = R.string.difficulty),
                                                modifier = Modifier
                                                    .size(25.dp)
                                                    .padding(start = 5.dp),
                                                tint = Color(0xFF974BA7)
                                            )
                                        }
                                    } else if (it.difficulty == Difficulty.MEDIUM){
                                        repeat(2) {
                                            Icon(
                                                Icons.Filled.Pets,
                                                contentDescription = stringResource(id = R.string.difficulty),
                                                modifier = Modifier
                                                    .size(25.dp)
                                                    .padding(start = 5.dp),
                                                tint = Color(0xFF974BA7)

                                            )
                                        }
                                    } else if (it.difficulty == Difficulty.HARD) {
                                        repeat(3) {
                                            Icon(
                                                Icons.Filled.Pets,
                                                contentDescription = stringResource(id = R.string.difficulty),
                                                modifier = Modifier
                                                    .size(25.dp)
                                                    .padding(start = 5.dp),
                                                tint = Color(0xFF974BA7)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TrainingScreenPreview(){
    val navController = rememberNavController()
    HealthyPawsTheme {
        TrainingScreen(navController)
    }
}