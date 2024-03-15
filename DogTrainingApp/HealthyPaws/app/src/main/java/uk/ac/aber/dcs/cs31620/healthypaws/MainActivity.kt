package uk.ac.aber.dcs.cs31620.healthypaws

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.healthypaws.model.CalendarEventViewModel
import uk.ac.aber.dcs.cs31620.healthypaws.model.DietViewModel
import uk.ac.aber.dcs.cs31620.healthypaws.model.DogViewModel
import uk.ac.aber.dcs.cs31620.healthypaws.model.ExercisesViewModel
import uk.ac.aber.dcs.cs31620.healthypaws.ui.calendar.CalendarScreen
import uk.ac.aber.dcs.cs31620.healthypaws.ui.calendar.ListOfEventsScreen
import uk.ac.aber.dcs.cs31620.healthypaws.ui.clicker.ClickerScreen
import uk.ac.aber.dcs.cs31620.healthypaws.ui.diet.*
import uk.ac.aber.dcs.cs31620.healthypaws.ui.exercises.*
import uk.ac.aber.dcs.cs31620.healthypaws.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.healthypaws.ui.profile.AddDogScreenTopLevel
import uk.ac.aber.dcs.cs31620.healthypaws.ui.profile.DogListScreen
import uk.ac.aber.dcs.cs31620.healthypaws.ui.profile.DogProfileScreen
import uk.ac.aber.dcs.cs31620.healthypaws.ui.start.StartScreen
import uk.ac.aber.dcs.cs31620.healthypaws.ui.theme.HealthyPawsTheme
import uk.ac.aber.dcs.cs31620.healthypaws.ui.training.TrainingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "event_channel_id",
                "Event Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        setContent {
            HealthyPawsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BuildNavigationGraph()
                }
            }
        }
    }
}

@Composable
private fun BuildNavigationGraph(
    exercisesViewModel: ExercisesViewModel = viewModel(),
    dietViewModel: DietViewModel = viewModel(),
    dogViewModel: DogViewModel = viewModel(),
    calendarEventViewModel: CalendarEventViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Start.route
    ) {
        composable(Screen.Start.route) { StartScreen(navController)}
        composable(Screen.Profile.route) { DogProfileScreen(
            navController,
            dogViewModel
        )}
        composable(Screen.Training.route) { TrainingScreen(navController, exercisesViewModel) }
        composable(Screen.Clicker.route) { ClickerScreen(navController)}
        composable(Screen.Events.route) { CalendarScreen(navController, calendarEventViewModel)}
        composable(Screen.Diet.route) { DietScreen(navController, dietViewModel)}
        composable(Screen.DietHome.route) { DietHomeScreen(navController)}
        composable(Screen.Exercises.route) { ExercisesScreen(navController, exercisesViewModel)}
        composable(Screen.Games.route) {}
        composable(Screen.ExerciseDetails.route) { ExerciseDetailsScreen(
            exercise = exercisesViewModel.selectedExercise.value!!,
            navController = navController
        )}
        composable(Screen.DogList.route) { DogListScreen(navController, dogViewModel, exercisesViewModel)}
        composable(Screen.AddDog.route) { AddDogScreenTopLevel(navController, dogViewModel) }
        composable(Screen.ListOfEvents.route) { ListOfEventsScreen(navController, calendarEventViewModel)}
        composable(Screen.ArticleOne.route) { ArticleOneScreen(navController) }
        composable(Screen.ArticleTwo.route) { ArticleTwoScreen(navController) }
        composable(Screen.ArticleThree.route) { ArticleThreeScreen(navController) }
        composable(Screen.ExerciseDescriptionStepOne.route) {ExerciseDescriptionStepOneScreen(
            exercise = exercisesViewModel.selectedExercise.value!!,
            navController = navController
        )}
        composable(Screen.ExerciseDescriptionStepTwo.route) {ExerciseDescriptionStepTwoScreen(
            exercise = exercisesViewModel.selectedExercise.value!!,
            navController = navController
        )}
        composable(Screen.ExerciseDescriptionStepThree.route) {ExerciseDescriptionStepThreeScreen(
            exercise = exercisesViewModel.selectedExercise.value!!,
            navController = navController
        )}
    }
}