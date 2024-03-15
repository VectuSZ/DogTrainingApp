package uk.ac.aber.dcs.cs31620.healthypaws.ui.exercises

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import uk.ac.aber.dcs.cs31620.healthypaws.model.Exercise
import uk.ac.aber.dcs.cs31620.healthypaws.model.ExercisesViewModel
import uk.ac.aber.dcs.cs31620.healthypaws.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.healthypaws.R
import uk.ac.aber.dcs.cs31620.healthypaws.ui.navigation.Screen

@Composable
fun ExerciseDetailsScreen(
    exercise: Exercise,
    navController: NavHostController,
    exercisesViewModel: ExercisesViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val title by remember { mutableStateOf(exercise.name)}

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
            ExerciseDetailsScreenContent(
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
fun ExerciseDetailsScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    exercise: Exercise,
    exercisesViewModel: ExercisesViewModel = viewModel()
) {
    ConstraintLayout {

        val (exerciseImage, exerciseDescription, goToExButton) = createRefs()

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .padding(15.dp)
                .constrainAs(exerciseImage) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
            colors = CardDefaults.cardColors(Color(0xFFFFFBFE))
        ) {
            Box(modifier = Modifier
                .align(Alignment.CenterHorizontally)) {
                GlideImage(
                    model = Uri.parse(exercise.imagePath),
                    contentDescription = stringResource(id = R.string.dog_exercise_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(300.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF8C8C8C))
                )
            }
        }

        Card(modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(start = 15.dp, end = 15.dp)
            .constrainAs(exerciseDescription) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(exerciseImage.bottom)
                bottom.linkTo(goToExButton.top)
            },
            colors = CardDefaults.cardColors(Color(0xFFFFFBFE))
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = exercise.shortDescription,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light
                )
            }
        }

        Button(
            onClick = {
                navController.navigate(Screen.ExerciseDescriptionStepOne.route)
            },
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomStart = 20.dp,
                bottomEnd = 20.dp
            ),
            colors = ButtonDefaults.buttonColors(Color(0xFF974BA7)),
            modifier = Modifier
                .width(280.dp)
                .height(70.dp)
                .padding(bottom = 20.dp)
                .constrainAs(goToExButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(exerciseDescription.bottom)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            Text(
                text = stringResource(id = R.string.go_to_ex_description),
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}