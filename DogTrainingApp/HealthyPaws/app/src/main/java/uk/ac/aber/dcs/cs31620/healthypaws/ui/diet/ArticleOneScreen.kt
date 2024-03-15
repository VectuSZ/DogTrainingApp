package uk.ac.aber.dcs.cs31620.healthypaws.ui.diet

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import uk.ac.aber.dcs.cs31620.healthypaws.R
import uk.ac.aber.dcs.cs31620.healthypaws.ui.components.TopLevelScaffold

@Composable
fun ArticleOneScreen(
    navController: NavHostController,
) {
    val coroutineScope = rememberCoroutineScope()
    val title by remember { mutableStateOf("Article") }

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
            ArticleOneScreenContent(
                modifier = Modifier,
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
private fun ArticleOneScreenContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {

            GlideImage(
                model = Uri.parse("file:///android_asset/images/article1.png"),
                contentDescription = stringResource(id = R.string.article1_image),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 15.dp)
            )

            Spacer(modifier = Modifier.height(1.dp))

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Medium, fontSize = 25.sp)) {
                        append(stringResource(id = R.string.article1_title_part1))
                    }
                    append("\n")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Light, fontSize = 20.sp)) {
                        append(stringResource(id = R.string.article1_title_part2))
                    }
                },
                modifier = Modifier
                    .padding(15.dp),
                textAlign = TextAlign.Start,
            )

            Spacer(modifier = Modifier.height(1.dp))

            Text(
                text = stringResource(id = R.string.article1_paragraph1),
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                lineHeight = 25.sp,
                modifier = Modifier
                    .padding(15.dp)
            )

            Spacer(modifier = Modifier.height(1.dp))

            Text(
                text = stringResource(id = R.string.article1_paragraph2),
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                lineHeight = 25.sp,
                modifier = Modifier
                    .padding(15.dp)
            )

            Spacer(modifier = Modifier.height(1.dp))

            Text(
                text = stringResource(id = R.string.article1_paragraph3),
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                lineHeight = 25.sp,
                modifier = Modifier
                    .padding(15.dp)
            )

            Spacer(modifier = Modifier.height(1.dp))

            Text(
                text = stringResource(id = R.string.article1_paragraph4),
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                lineHeight = 25.sp,
                modifier = Modifier
                    .padding(15.dp)
            )

            Spacer(modifier = Modifier.height(1.dp))

            Text(
                text = stringResource(id = R.string.article1_paragraph5),
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                lineHeight = 25.sp,
                modifier = Modifier
                    .padding(15.dp)
            )

            Spacer(modifier = Modifier.height(1.dp))

            Text(
                text = stringResource(id = R.string.article1_paragraph6),
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                lineHeight = 25.sp,
                modifier = Modifier
                    .padding(15.dp)

            )

            Spacer(modifier = Modifier.height(1.dp))

            Text(
                text = stringResource(id = R.string.references),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                lineHeight = 25.sp,
                modifier = Modifier
                    .padding(15.dp)
            )

            Spacer(modifier = Modifier.height(1.dp))

            Text(
                text = stringResource(id = R.string.article1_reference1),
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                lineHeight = 25.sp,
                modifier = Modifier
                    .padding(15.dp)
            )
        }
}