package uk.ac.aber.dcs.cs31620.healthypaws.ui.navigation

sealed class Screen(
    val route: String
) {
    object Start : Screen("start")
    object Training : Screen("training")
    object Profile : Screen("profile")
    object Events : Screen("events")
    object Diet : Screen("diet")
    object Exercises : Screen("exercises")
    object Games : Screen("games")
    object Clicker : Screen("clicker")
    object ExerciseDetails: Screen("exercise details")
    object ExerciseFullDescription : Screen("exercise full description")
    object ExerciseDescriptionStepOne : Screen("exercise step one")
    object ExerciseDescriptionStepTwo : Screen("exercise step two")
    object ExerciseDescriptionStepThree : Screen("exercise step three")
    object DogList : Screen("dog list")
    object AddDog : Screen("add dog")
    object DietHome : Screen("diet home")
    object ListOfEvents : Screen("list of events")
    object ArticleOne : Screen("article one")
    object ArticleTwo : Screen("article two")
    object ArticleThree : Screen("article three")
}

val screens = listOf(
    Screen.Training,
    Screen.Profile,
    Screen.Events,
    Screen.DietHome
)