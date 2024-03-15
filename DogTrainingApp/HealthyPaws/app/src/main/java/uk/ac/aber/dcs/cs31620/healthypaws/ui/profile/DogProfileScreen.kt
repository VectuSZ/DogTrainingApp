package uk.ac.aber.dcs.cs31620.healthypaws.ui.profile

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import uk.ac.aber.dcs.cs31620.healthypaws.R
import uk.ac.aber.dcs.cs31620.healthypaws.model.Dog
import uk.ac.aber.dcs.cs31620.healthypaws.model.DogViewModel
import uk.ac.aber.dcs.cs31620.healthypaws.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.healthypaws.ui.navigation.Screen
import java.time.format.DateTimeFormatter

@Composable
fun DogProfileScreen(
    navController: NavHostController,
    dogViewModel: DogViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val title by remember { mutableStateOf("Dog Profile") }
    val dogList by dogViewModel.dogList.observeAsState(listOf())


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
           DogProfileScreenContent(
               modifier = Modifier,
               navController = navController,
               dogList = dogList,
               dogViewModel = dogViewModel
           )
       }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
private fun DogProfileScreenContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    dogViewModel: DogViewModel = viewModel(),
    dogList: List<Dog> = listOf(),
) {

    val state = rememberLazyListState()
    var expanded by remember { mutableStateOf(false)}

    if(dogList.isNotEmpty()){

    var dog = dogList[dogViewModel.numOfDog]

        val formattedDogDob by remember {
            derivedStateOf {
                DateTimeFormatter
                    .ofPattern("dd.MM.yyyy")
                    .format(dog.dateOfBirth)
            }
        }

    ConstraintLayout {

        val (profileCard, commandsCard, commands) = createRefs()

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(start = 15.dp, end =15.dp, bottom = 15.dp, top = 30.dp)
                .constrainAs(profileCard) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
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
                        .width(160.dp)
                ) {
                    GlideImage(
                        model = Uri.parse(dog.imagePath),
                        contentDescription = stringResource(id = R.string.dog_exercise_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .size(150.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF8C8C8C))
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(200.dp)
                        .align(Alignment.CenterEnd)
                ) {
                    Text(
                        text = dog.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 20.dp)
                    )

                    Text(
                        text = dog.breed,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(bottom = 50.dp),
                        color = Color(0xFF974BA7)
                    )

                    Text(
                        text = formattedDogDob,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(bottom = 15.dp)
                    )

                    IconButton(
                        onClick = {
                            expanded = true
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            Icons.Filled.MoreVert,
                            contentDescription = stringResource(id = R.string.menu),
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        },
                        offset = DpOffset(x = 50.dp, y = -110.dp)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(id = R.string.remove_dog)
                                )
                            },
                            onClick = {
                            deleteDog(
                                dog = dog,
                                doDelete = { existingDog ->
                                    dogViewModel.deleteDog(existingDog)
                                }
                            )
                                navController.navigate(Screen.DogList.route)
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Remove,
                                    contentDescription = null
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(id = R.string.add_dog)
                                )
                            },
                            onClick = {
                                navController.navigate(Screen.AddDog.route)
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Add,
                                    contentDescription = null
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(id = R.string.change_dog)
                                )
                            },
                            onClick = {
                                navController.navigate(Screen.DogList.route)
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.SwapHoriz,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(top = 41.dp, start = 20.dp, end = 20.dp,)
                .constrainAs(commandsCard) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(profileCard.bottom)
                },
            colors = CardDefaults.cardColors(Color(0xFFFFFBFE))
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = stringResource(id = R.string.commands_i_know),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(end = 30.dp)
                        .align(Alignment.CenterStart)
                )
            }

            Divider()
        }

        dogViewModel.currentListOfKnownCommands = dog.knownCommands.toMutableList()


        LazyColumn(
            state = state,
            contentPadding = PaddingValues(10.dp),
            modifier = Modifier
                .constrainAs(commands){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(commandsCard.bottom)
                }

        ) {
            items(dogViewModel.currentListOfKnownCommands) {
                Card(
                    modifier = Modifier
                        .height(90.dp)
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                        .fillMaxSize(),
                    colors = CardDefaults.cardColors(Color(0xFFFBF3FC))
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
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF8C8C8C))
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(240.dp)
                                .align(Alignment.CenterEnd)
                        ) {
                            Text(
                                text = it.name,
                                fontSize = 17.sp,
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
    } else if (dogList.isEmpty()){
        ConstraintLayout {

            val (emptyText, button) = createRefs()

            Text(
                text = stringResource(id = R.string.no_dogs),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(10.dp)
                    .constrainAs(emptyText){
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Button(
                onClick = {
                    navController.navigate(Screen.AddDog.route)
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
                    .padding(top = 20.dp)
                    .constrainAs(button) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(emptyText.bottom)
                    }
            ) {
                Text(
                    text = stringResource(id = R.string.add_dog),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

private fun deleteDog(
    dog: Dog,
    doDelete: (Dog) -> Unit = {}
) {
    if (dog.name.isNotEmpty()) {
        doDelete(dog)
    }
}