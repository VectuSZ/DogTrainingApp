package uk.ac.aber.dcs.cs31620.healthypaws.ui.diet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.ac.aber.dcs.cs31620.healthypaws.model.DietViewModel
import uk.ac.aber.dcs.cs31620.healthypaws.R
import uk.ac.aber.dcs.cs31620.healthypaws.model.Diet
import uk.ac.aber.dcs.cs31620.healthypaws.ui.theme.HealthyPawsTheme

@Composable
fun AddDietDialog(
    dietViewModel: DietViewModel = viewModel(),
    dialogIsOpen: Boolean,
    dialogOpen: (Boolean) -> Unit = { },
) {

    var dietName by remember { mutableStateOf("") }
    var dietDescription by remember { mutableStateOf("") }

    if (dialogIsOpen) {
        Dialog(
            onDismissRequest = {
                dialogOpen(false)
            }
        ) {
            Surface(
                modifier = Modifier
                    .size(350.dp),
                shape = RoundedCornerShape(
                    topStart = 50.dp,
                    topEnd = 50.dp,
                    bottomStart = 50.dp,
                    bottomEnd = 50.dp
                ),
                border = BorderStroke(width = 2.dp, Color(0xFF00000D)),
            ) {
                ConstraintLayout {

                    val (addDietNameLabel, addDietDescriptionLabel, goForwardButton) = createRefs()


                    TextField(
                        value = dietName,
                        onValueChange = { dietName = it },
                        label = { Text(stringResource(id = R.string.add_diet_name)) },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(0xFFFFFBFE),
                        ),
                        placeholder = {
                            Text(stringResource(id = R.string.diet_name),
                                color = Color.Gray)
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    dietName = ""
                                }
                            ) {
                                Icon(
                                    Icons.Filled.Backspace,
                                    contentDescription = stringResource(id = R.string.backspace)
                                )
                            }
                        },
                        modifier = Modifier
                            .padding(top = 50.dp, start = 30.dp, end = 30.dp)
                            .constrainAs(addDietNameLabel) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                            }
                    )

                    TextField(
                        value = dietDescription,
                        onValueChange = { dietDescription = it },
                        label = { Text(stringResource(id = R.string.add_diet_description)) },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(0xFFFFFBFE),
                        ),
                        placeholder = {
                            Text(stringResource(id = R.string.diet_description),
                            color = Color.Gray)
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    dietDescription = ""
                                }
                            ) {
                                Icon(
                                    Icons.Filled.Backspace,
                                    contentDescription = stringResource(id = R.string.backspace)
                                )
                            }
                        },
                        modifier = Modifier
                            .padding(top = 40.dp, start = 30.dp, end = 30.dp)
                            .constrainAs(addDietDescriptionLabel) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(addDietNameLabel.bottom)
                            }
                    )

                    FloatingActionButton(
                        modifier = Modifier
                            .padding(top = 50.dp)
                            .constrainAs(goForwardButton) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(addDietDescriptionLabel.bottom)
                            },
                        onClick = {
                            insertWord(
                                dietName = dietName,
                                dietDescription = dietDescription,
                                doInsert = { newDiet ->
                                    dietViewModel.insertDiet(newDiet)
                                }
                            )
                            dialogOpen(false)


                        },
                        containerColor = Color(0xFF974BA7)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowForwardIos,
                            contentDescription = stringResource(R.string.go_forward)
                        )
                    }
                }
            }
        }
    }
}

private fun insertWord(
    dietName: String,
    dietDescription: String,
    doInsert: (Diet) -> Unit = {}
){
    if(dietName.isNotEmpty() && dietDescription.isNotEmpty()){
        val diet = Diet(
            id = 0,
            name = dietName,
            description = dietDescription
        )
        doInsert(diet)
    }
}

@Preview
@Composable
fun DistanceDialogPreview(){
    HealthyPawsTheme {
        AddDietDialog(dialogIsOpen = true)
    }
}