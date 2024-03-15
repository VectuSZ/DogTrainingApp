package uk.ac.aber.dcs.cs31620.healthypaws.ui.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.healthypaws.R
import uk.ac.aber.dcs.cs31620.healthypaws.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.healthypaws.ui.theme.HealthyPawsTheme

@Composable
fun StartScreen(
    navController: NavHostController,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        StartScreenContent(modifier = Modifier, navController)
    }
}

@Composable
private fun StartScreenContent(modifier: Modifier = Modifier, navController: NavHostController) {

    ConstraintLayout {
        val (startScreenBackground, title, description, getStartedButton, dismissButton) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.astrodog),
            contentDescription = stringResource(id = R.string.start_screen_background),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(startScreenBackground) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = stringResource(id = R.string.healthy_paws),
            fontSize = 38.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(top = 50.dp)
                .constrainAs(title) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(description.top)
                }
        )

        Text(
            text = stringResource(id = R.string.healthy_paws_description),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 10.dp, start = 30.dp, end = 30.dp)
                .constrainAs(description) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(title.bottom)
                }
        )

        Button(
            onClick = {
                navController.navigate(Screen.Training.route)
            },
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomStart = 20.dp,
                bottomEnd = 20.dp
            ),
            colors = ButtonDefaults.buttonColors(Color(0xFFCC7DDA)),
            modifier = Modifier
                .padding(top = 480.dp)
                .width(280.dp)
                .height(70.dp)
                .constrainAs(getStartedButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(description.bottom)
                    bottom.linkTo(dismissButton.top)
                }
        ) {
            Text(
                text = stringResource(id = R.string.get_started_button),
                fontSize = 20.sp,
                color = Color(0xFF000000)
            )
        }

        TextButton(
            onClick = {

            },
            modifier = Modifier
                .constrainAs(dismissButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(getStartedButton.bottom)
                }
        ) {
            Text(
                text = stringResource(id = R.string.dismiss_button),
                fontSize = 20.sp,
                color = Color(0xFFFFFFFF)
            )
        }
    }
}

@Preview
@Composable
fun StartScreenPreview() {
    val navController = rememberNavController()
    HealthyPawsTheme {
        StartScreen(navController)
    }
}
