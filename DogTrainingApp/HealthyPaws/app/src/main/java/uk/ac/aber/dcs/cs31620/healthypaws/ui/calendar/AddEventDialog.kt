package uk.ac.aber.dcs.cs31620.healthypaws.ui.calendar

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import uk.ac.aber.dcs.cs31620.healthypaws.R
import uk.ac.aber.dcs.cs31620.healthypaws.model.CalendarEvent
import uk.ac.aber.dcs.cs31620.healthypaws.model.CalendarEventViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun AddEventDialog(
    calendarEventViewModel: CalendarEventViewModel = viewModel(),
    dialogIsOpen: Boolean,
    dialogOpen: (Boolean) -> Unit = { },
    context: Context
) {

    var eventName by remember { mutableStateOf("") }
    var eventDate by remember { mutableStateOf(LocalDate.now()) }
    var eventTime by remember { mutableStateOf(LocalTime.now()) }

    val formattedEventDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(eventDate)
        }
    }

    val formattedEventTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("HH:mm")
                .format(eventTime)
        }
    }

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

                    val (addEventName, addEventDate, addEventTime, goForwardButton) = createRefs()


                    EventNameInput(
                        eventName = eventName,
                        modifier = Modifier
                            .padding(top = 20.dp, start = 30.dp, end = 30.dp)
                            .fillMaxWidth()
                            .constrainAs(addEventName) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(addEventDate.top)
                            },
                        updateName = {
                            eventName = it
                        }
                    )

                    EventDateInput(
                        eventDate = formattedEventDate,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 30.dp, end = 30.dp)
                            .fillMaxWidth()
                            .constrainAs(addEventDate) {
                                top.linkTo(addEventName.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(addEventTime.top)
                            },
                        updateDate = {
                            eventDate = it
                        }
                    )

                    EventTimeInput(
                        eventTime = formattedEventTime,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 30.dp, end = 30.dp)
                            .fillMaxWidth()
                            .constrainAs(addEventTime) {
                                top.linkTo(addEventDate.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(goForwardButton.top)
                            },
                        updateTime = {
                            eventTime = it
                        }
                    )

                    FloatingActionButton(
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp)
                            .constrainAs(goForwardButton) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(addEventTime.bottom)
                                bottom.linkTo(parent.bottom)
                            },
                        onClick = {
                            insertEvent(
                                title = eventName,
                                date = eventDate,
                                time = eventTime,
                                doInsert = { newEvent ->
                                    calendarEventViewModel.insertEvent(newEvent)
                                    newEvent.scheduleNotifications(context)
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

private fun insertEvent(
    title: String,
    date: LocalDate,
    time: LocalTime,
    doInsert: (CalendarEvent) -> Unit = {},
) {

    if (title.isNotEmpty()){
        val event = CalendarEvent(
            id = 0,
            title = title,
            date = date,
            time = time
        )
        doInsert(event)
    }
}

@Composable
fun EventNameInput(
    eventName: String,
    modifier: Modifier,
    updateName: (String) -> Unit
) {
    OutlinedTextField(
        value = eventName,
        label = {
            Text(text = stringResource(id = R.string.event_name))
        },
        onValueChange = { updateName(it) },
        modifier = modifier,
        placeholder = {
            Text(stringResource(id = R.string.event_name),
                color = Color.Gray)
        },
        shape = RoundedCornerShape(30.dp)
    )
}

@Composable
fun EventDateInput(
    eventDate: String,
    modifier: Modifier,
    updateDate: (LocalDate) -> Unit
) {
    val dateDialogState = rememberMaterialDialogState()

    OutlinedButton(
        onClick = {
            dateDialogState.show()
        },
        modifier = modifier,
    ) {
        Icon(
            Icons.Filled.CalendarToday,
            contentDescription = stringResource(id = R.string.calendar_icon),
            modifier = Modifier
                .size(25.dp)
                .padding(end = 10.dp)
        )
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.event_date))
                append(" ")
                append(eventDate)
            },
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            color = Color(0xFF000000)
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
            updateDate(it)
        }

    }
}

@Composable
fun EventTimeInput(
    eventTime: String,
    modifier: Modifier,
    updateTime: (LocalTime) -> Unit
) {
    val timeDialogState = rememberMaterialDialogState()

    OutlinedButton(
        onClick = {
            timeDialogState.show()
        },
        modifier = modifier,
    ) {
        Icon(
            Icons.Filled.Schedule,
            contentDescription = stringResource(id = R.string.time_icon),
            modifier = Modifier
                .size(25.dp)
                .padding(end = 10.dp)
        )
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.event_time))
                append(" ")
                append(eventTime)
            },
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            color = Color(0xFF000000)
        )
    }

    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(text = stringResource(R.string.ok))
            negativeButton(text = stringResource(R.string.cancel))
        }
    ) {
        timepicker(
            initialTime = LocalTime.now(),
            title = stringResource(R.string.pickTime)
        ) {
            updateTime(it)
        }

    }
}