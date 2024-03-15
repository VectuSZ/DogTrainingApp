package uk.ac.aber.dcs.cs31620.healthypaws.ui.diet

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import uk.ac.aber.dcs.cs31620.healthypaws.R
import uk.ac.aber.dcs.cs31620.healthypaws.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.healthypaws.ui.navigation.Screen

@Composable
fun DietHomeScreen(
    navController: NavHostController,
) {
    val coroutineScope = rememberCoroutineScope()
    val title by remember { mutableStateOf("Diet") }

    TopLevelScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        title = title,
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            DietHomeScreenContent(
                modifier = Modifier,
                navController = navController,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
private fun DietHomeScreenContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {

    ConstraintLayout {

        val (dietCard, articlesTitle, article1, article2, article3) = createRefs()

        Card(
            onClick = {
                navController.navigate(Screen.Diet.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                .constrainAs(dietCard) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(articlesTitle.top)
                },
            colors = CardDefaults.cardColors(Color(0xFFFFFBFE)),
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomStart = 20.dp,
                bottomEnd = 20.dp
            ),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Icon(
                    Icons.Filled.MenuBook,
                    contentDescription = stringResource(id = R.string.meal_icon),
                    modifier = Modifier
                        .size(40.dp)
                        .padding(start = 15.dp)
                        .align(Alignment.CenterStart),
                    tint = Color(0xFF974BA7)
                )

                Text(
                    text = stringResource(id = R.string.go_to_diets),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(start = 60.dp)
                        .align(Alignment.CenterStart)
                )

                IconButton(
                    onClick = {
                        navController.navigate(Screen.Diet.route)
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
            }
        }

        Text(
            text = stringResource(id = R.string.articles),
            fontSize = 25.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(start = 20.dp, top = 5.dp, bottom = 5.dp)
                .constrainAs(articlesTitle) {
                    top.linkTo(dietCard.bottom)
                    start.linkTo(parent.start)
                }
        )

        Card(
            modifier = Modifier
                .height(150.dp)
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                .fillMaxSize()
                .clickable {
                    navController.navigate(Screen.ArticleOne.route)
                }
                .constrainAs(article1) {
                    top.linkTo(articlesTitle.bottom)
                    bottom.linkTo(article2.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            colors = CardDefaults.cardColors(Color(0xFFFAEFFC))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(150.dp)
                        .padding(start = 10.dp)
                ) {
                    GlideImage(
                        model = Uri.parse("file:///android_asset/images/article1.png"),
                        contentDescription = stringResource(id = R.string.article1_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .size(110.dp)
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
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Medium, fontSize = 18.sp)) {
                                append(stringResource(id = R.string.article1_title_part1))
                            }
                            append("\n")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Light, fontSize = 16.sp)) {
                                append(stringResource(id = R.string.article1_title_part2))
                            }
                        },
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, top = 30.dp, bottom = 20.dp),
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .height(150.dp)
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                .fillMaxSize()
                .clickable {
                    navController.navigate(Screen.ArticleTwo.route)
                }
                .constrainAs(article2) {
                    top.linkTo(article1.bottom)
                    bottom.linkTo(article3.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            colors = CardDefaults.cardColors(Color(0xFFF1D4F6))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(150.dp)
                        .padding(start = 10.dp)
                ) {
                    GlideImage(
                        model = Uri.parse("file:///android_asset/images/article2.png"),
                        contentDescription = stringResource(id = R.string.article2_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .size(110.dp)
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
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Medium, fontSize = 18.sp)) {
                                append(stringResource(id = R.string.article2_title_part1))
                            }
                            append("\n")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Light, fontSize = 16.sp)) {
                                append(stringResource(id = R.string.article2_title_part2))
                            }
                        },
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, top = 30.dp, bottom = 20.dp),
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp,
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .height(150.dp)
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                .fillMaxSize()
                .clickable {
                    navController.navigate(Screen.ArticleThree.route)
                }
                .constrainAs(article3) {
                    top.linkTo(article2.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            colors = CardDefaults.cardColors(Color(0xFFF5BEFF))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(150.dp)
                        .padding(start = 10.dp)
                ) {
                    GlideImage(
                        model = Uri.parse("file:///android_asset/images/article3.png"),
                        contentDescription = stringResource(id = R.string.article3_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .size(110.dp)
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
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Medium, fontSize = 18.sp)) {
                                append(stringResource(id = R.string.article3_title_part1))
                            }
                            append("\n")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Light, fontSize = 16.sp)) {
                                append(stringResource(id = R.string.article3_title_part2))
                            }
                        },
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, top = 40.dp, bottom = 20.dp),
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp,
                    )
                }
            }
        }
    }
}