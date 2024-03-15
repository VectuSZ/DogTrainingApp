package uk.ac.aber.dcs.cs31620.healthypaws.ui.profile

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import uk.ac.aber.dcs.cs31620.healthypaws.model.Dog
import uk.ac.aber.dcs.cs31620.healthypaws.model.DogViewModel
import uk.ac.aber.dcs.cs31620.healthypaws.R
import uk.ac.aber.dcs.cs31620.healthypaws.model.Exercise
import uk.ac.aber.dcs.cs31620.healthypaws.model.util.ResourceUtil
import java.io.File
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun AddDogScreenTopLevel(
    navController: NavHostController,
    dogViewModel: DogViewModel = viewModel()
) {
    AddDogScreen(
        navController = navController,
        insertDog = { newDog ->
            dogViewModel.insertDog(newDog)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDogScreen(
    navController: NavHostController,
    insertDog: (Dog) -> Unit = {}
) {

    var dogName by rememberSaveable { mutableStateOf("") }
    var dogBreed by rememberSaveable { mutableStateOf( "") }
    var dogDateOfBirth by remember { mutableStateOf(LocalDate.now())}
    val defaultImagePath = stringResource(R.string.default_image_path)
    var imagePath by rememberSaveable { mutableStateOf(defaultImagePath) }
    val dogKnownCommands by rememberSaveable { mutableStateOf(setOf<Exercise>())}

    val formattedDogDob by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(dogDateOfBirth)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    insertDog(
                        name = dogName,
                        breed = dogBreed,
                        dob = dogDateOfBirth,
                        knownCommands = dogKnownCommands,
                        imagePath = imagePath,
                        doInsert = { newDog ->
                            insertDog(newDog)
                        }
                    )
                    navController.navigateUp()
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add_dog)
                )
            }
        },
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(stringResource(R.string.add_dog))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.goBack)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            DogImage(
                imagePath = imagePath,
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp),
                updateImagePath = {
                    imagePath = it
                }
            )

            DogNameInput(
                dogName = dogName,
                modifier = Modifier
                    .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth(),
                updateName = {
                    dogName = it
                }
            )

            DogBreedInput(
                dogBreed = dogBreed,
                modifier = Modifier
                    .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth(),
                updateBreed = {
                    dogBreed = it
                }
            )

            DogDateOfBirthInput(
                dogDob = formattedDogDob,
                modifier = Modifier
                    .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth(),
                updateDob = {
                    dogDateOfBirth = it
                }
            )

        }
    }
}

private fun insertDog(
    name: String,
    breed: String,
    dob: LocalDate,
    knownCommands: Set<Exercise>,
    imagePath: String,
    doInsert: (Dog) -> Unit = {}
) {

    if (name.isNotEmpty() && imagePath.isNotEmpty()){
        val dog = Dog(
            id = 0,
            name = name,
            breed = breed,
            dateOfBirth = dob,
            knownCommands = knownCommands,
            imagePath = imagePath,
        )
        doInsert(dog)
    }
}

@Composable
fun DogNameInput(
    dogName: String,
    modifier: Modifier,
    updateName: (String) -> Unit
) {
    OutlinedTextField(
        value = dogName,
        label = {
            Text(text = stringResource(id = R.string.dog_name))
        },
        onValueChange = { updateName(it) },
        modifier = modifier
    )
}

@Composable
fun DogBreedInput(
    dogBreed: String,
    modifier: Modifier,
    updateBreed: (String) -> Unit
) {
    OutlinedTextField(
        value = dogBreed,
        label = {
            Text(text = stringResource(id = R.string.dog_breed))
        },
        onValueChange = { updateBreed(it) },
        modifier = modifier
    )
}

@Composable
fun DogDateOfBirthInput(
    dogDob: String,
    modifier: Modifier,
    updateDob: (LocalDate) -> Unit
) {
    val dateDialogState = rememberMaterialDialogState()

    OutlinedButton(
        onClick = {
            dateDialogState.show()
        },
        modifier = modifier
    ) {
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.dog_date_of_birth))
                append(" ")
                append(dogDob)
            },
            textAlign = TextAlign.Start,
            fontSize = 16.sp
        )
    }

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = stringResource(R.string.ok))
            negativeButton(text = stringResource(R.string.cancel))
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = stringResource(R.string.pickDate)
        ) {
            updateDob(it)
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun DogImage(
    imagePath: String,
    modifier: Modifier,
    updateImagePath: (String) -> Unit = {}
) {
    var photoFile: File? = remember { null }
    val ctx = LocalContext.current


    val resultLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                updateImagePath(
                    "file://${photoFile!!.absolutePath}"
                )
            }
        }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {

        GlideImage(
            model = Uri.parse(imagePath),
            contentDescription = stringResource(R.string.dog_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(200.dp)
                .clickable {
                    takePicture(
                        ctx = ctx,
                        resultLauncher = resultLauncher,
                    ) {
                        photoFile = it
                    }
                }
        )
        Text(text = stringResource(id = R.string.enterImageMsg))
    }

}

private fun takePicture(
    ctx: Context,
    resultLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    updateFile: (File) -> Unit
) {
    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    var photoFile: File? = null

    try {
        photoFile = ResourceUtil.createImageFile(ctx)
    } catch (ex: IOException) {
        Toast.makeText(
            ctx,
            ctx.getString(R.string.cannot_create_image_file),
            Toast.LENGTH_SHORT
        ).show()
    }

    photoFile?.let {
        val photoUri = FileProvider.getUriForFile(
            ctx,
            ctx.packageName,
            it
        )
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        try {
            resultLauncher.launch(takePictureIntent)
            updateFile(photoFile)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(ctx, R.string.cannotTakePicture, Toast.LENGTH_LONG)
                .show()
        }
    }

}

@Composable
private fun HandleBackButton(navController: NavHostController) {
    val backCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                navController.navigateUp()
            }
        }

    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, backDispatcher) {
        backDispatcher.addCallback(lifecycleOwner, backCallback)
        onDispose {
            backCallback.remove()
        }
    }

}