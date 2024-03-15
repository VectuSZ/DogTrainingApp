package uk.ac.aber.dcs.cs31620.healthypaws.ui.exercises

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import uk.ac.aber.dcs.cs31620.healthypaws.R
import uk.ac.aber.dcs.cs31620.healthypaws.model.Exercise
import uk.ac.aber.dcs.cs31620.healthypaws.model.ExercisesViewModel
import uk.ac.aber.dcs.cs31620.healthypaws.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.healthypaws.ui.navigation.Screen

@Composable
fun ExerciseDescriptionStepOneScreen(
    exercise: Exercise,
    navController: NavHostController,
    exercisesViewModel: ExercisesViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val title by remember { mutableStateOf(exercise.name) }

    TopLevelScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        title = title
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            ExerciseDescriptionStepOneScreenContent(
                modifier = Modifier,
                navController = navController,
                exercise = exercise,
                exercisesViewModel = exercisesViewModel
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDescriptionStepOneScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    exercise: Exercise,
    exercisesViewModel: ExercisesViewModel = viewModel()
) {
    ConstraintLayout {

        val (exerciseImage, exerciseDescription, goToExButton, stepOne, progress) = createRefs()

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(15.dp)
                .constrainAs(exerciseImage) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(progress.top)
                },
            colors = CardDefaults.cardColors(Color(0xFFFFFBFE))
        ) {
            Box(modifier = Modifier
                .align(Alignment.CenterHorizontally)) {
                GlideImage(
                    model = Uri.parse(exercise.imagePathList[0]),
                    contentDescription = stringResource(id = R.string.dog_exercise_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .width(280.dp)
                        .height(200.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color(0xFF8C8C8C))
                )
            }
        }

        LinearProgressIndicator(
            progress = 0.33f,
            modifier = Modifier
                .padding(10.dp)
                .constrainAs(progress){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(exerciseImage.bottom)
                    bottom.linkTo(stepOne.top)
                },
            color = Color(0xFF974BA7)
        )

        Card(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(start = 15.dp, end = 15.dp)
            .constrainAs(stepOne) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(progress.bottom)
                bottom.linkTo(exerciseDescription.top)
            },
            colors = CardDefaults.cardColors(Color(0xFFFFFBFE))
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(id = R.string.stepOne),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Card(modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(start = 15.dp, end = 15.dp)
            .constrainAs(exerciseDescription) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(stepOne.bottom)
                bottom.linkTo(goToExButton.top)
            },
            colors = CardDefaults.cardColors(Color(0xFFFFFBFE))
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = exercise.longDescription[0],
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light
                )
            }
        }

        Button(
            onClick = {
                navController.navigate(Screen.ExerciseDescriptionStepTwo.route)
            },
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomStart = 20.dp,
                bottomEnd = 20.dp
            ),
            colors = ButtonDefaults.buttonColors(Color(0xFFCC7DDA)),
            modifier = Modifier
                .width(280.dp)
                .height(90.dp)
                .padding(bottom = 30.dp)
                .constrainAs(goToExButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(exerciseDescription.bottom)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            Text(
                text = stringResource(id = R.string.next),
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}