package uk.ac.aber.dcs.cs31620.healthypaws.ui.clicker

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.healthypaws.R
import uk.ac.aber.dcs.cs31620.healthypaws.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.healthypaws.ui.theme.HealthyPawsTheme

@Composable
fun ClickerScreen(
    navController: NavHostController
) {
    val coroutineScope = rememberCoroutineScope()
    val title by remember { mutableStateOf("Clicker") }

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
            ClickerScreenContent(
                modifier = Modifier,
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ClickerScreenContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    ConstraintLayout {


        val (clickerImage, changeSoundButton) = createRefs()
        val context = LocalContext.current
        val soundPlayers = listOf(
            MediaPlayer.create(LocalContext.current, R.raw.whistle_sound1),
            MediaPlayer.create(LocalContext.current, R.raw.whistle_sound2),
            MediaPlayer.create(LocalContext.current, R.raw.whistle_sound3),
            MediaPlayer.create(LocalContext.current, R.raw.whistle_sound4),
            MediaPlayer.create(LocalContext.current, R.raw.whistle_sound5),
            MediaPlayer.create(LocalContext.current, R.raw.whistle_sound6),
        )

        var currentSoundIndex by rememberSaveable { mutableStateOf(0) }
        var changeSoundDialogIsOpen by rememberSaveable { mutableStateOf(false)}

        Image(
            painter = painterResource(id = R.drawable.whistle),
            contentDescription = stringResource(id = R.string.whistle_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(bottom = 50.dp)
                .size(350.dp)
                .clickable {
                    soundPlayers[currentSoundIndex].start()
                }
                .constrainAs(clickerImage) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Button(
            onClick = {
                changeSoundDialogIsOpen = true
            },
            shape = RoundedCornerShape(
                topStart = 30.dp,
                topEnd = 30.dp,
                bottomStart = 30.dp,
                bottomEnd = 30.dp
            ),
            colors = ButtonDefaults.buttonColors(Color(0xFF974BA7)),
            modifier = Modifier
                .width(260.dp)
                .height(70.dp)
                .constrainAs(changeSoundButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(clickerImage.bottom)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            Text(
                text = stringResource(id = R.string.change_clicker_sound),
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }

        if (changeSoundDialogIsOpen) {
            AlertDialog(
                onDismissRequest = { changeSoundDialogIsOpen = false },
                title = { Text(text = "Select a sound") },
                text = {
                    Column {
                        soundPlayers.indices.forEach { index ->
                            TextButton(
                                onClick = {
                                    // Stop the current sound, select the new sound, and close the dialog
                                    soundPlayers[currentSoundIndex].stop()
                                    currentSoundIndex = index
                                    changeSoundDialogIsOpen = false
                                }
                            ) {
                                if(index == 0) {
                                    Text(text = "Sound ${index + 1}")
                                } else if(index == 1){
                                    Text(text = "Sound ${index + 1}")
                                } else if(index == 2){
                                    Text(text = "Sound ${index + 1}")
                                } else if(index == 3){
                                    Text(text = "Sound ${index + 1}")
                                } else if(index == 4){
                                    Text(text = "Sound ${index + 1}")
                                } else if(index == 5){
                                    Text(text = "Sound ${index + 1}")
                                }                            }
                        }
                    }
                },
                confirmButton = {},
                dismissButton = {}
            )
        }
    }
}

@Preview
@Composable
fun ClickerScreenPreview(){
    val navController = rememberNavController()
    HealthyPawsTheme {
        ClickerScreen(navController)
    }
}