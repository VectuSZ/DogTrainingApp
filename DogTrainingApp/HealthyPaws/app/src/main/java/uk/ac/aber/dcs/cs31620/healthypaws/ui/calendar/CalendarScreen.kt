package uk.ac.aber.dcs.cs31620.healthypaws.ui.calendar

import android.annotation.SuppressLint
import android.widget.CalendarView
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.healthypaws.model.CalendarEvent
import uk.ac.aber.dcs.cs31620.healthypaws.model.CalendarEventViewModel
import uk.ac.aber.dcs.cs31620.healthypaws.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.healthypaws.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.healthypaws.ui.theme.HealthyPawsTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import uk.ac.aber.dcs.cs31620.healthypaws.R
import java.time.LocalTime
import java.time.format.TextStyle

@Composable
fun CalendarScreen(
    navController: NavHostController,
    calendarEventViewModel: CalendarEventViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val title by remember { mutableStateOf("Calendar") }
    val eventList by calendarEventViewModel.eventList.observeAsState(listOf())

    TopLevelScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        title = title
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            CalendarScreenContent(
                modifier = Modifier.padding(8.dp),
                navController = navController,
                eventList = eventList,
                calendarEventViewModel = calendarEventViewModel
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SimpleDateFormat")
@Composable
fun CalendarScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    eventList: List<CalendarEvent> = listOf(),
    calendarEventViewModel: CalendarEventViewModel = viewModel()
) {
    val eventsByDate = mutableMapOf<String, String>()
    var selectedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var selectedTime by remember {
        mutableStateOf(LocalTime.now())
    }
    var addDialogIsOpen by rememberSaveable { mutableStateOf(false) }

    for (event in eventList) {
        val dateString = event.date.format(DateTimeFormatter.ofPattern("d - M - u"))
        eventsByDate[dateString] = event.title
    }

    val selectedEventTitle = calendarEventViewModel.selectedEventTitle.observeAsState()
    val selectedEventTime = calendarEventViewModel.selectedEventTime.observeAsState()

    ConstraintLayout {

        val (dateRow, divider, calendar, eventText, addButton, goToListButton) = createRefs()

        AndroidView(
            modifier = Modifier
                .padding(bottom = 10.dp, top = 10.dp)
                .constrainAs(calendar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            factory = {
                CalendarView(it)
            },
            update = { calendarView ->
                calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
                    val newSelectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                    selectedDate = newSelectedDate
                    calendarEventViewModel.setSelectedDate(selectedDate)

                    val events = calendarEventViewModel.getEventsForDate(selectedDate)
                    eventsByDate[selectedDate.toString()] = events.firstOrNull()?.title ?: ""

                    calendarEventViewModel.setSelectedEventTitle(events.firstOrNull()?.title ?: "")
                    calendarEventViewModel.setSelectedEventTime(events.firstOrNull()?.time ?: LocalTime.now())

                    calendarView.date = selectedDate.toEpochDay() * 86400000
                }
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(48.dp)
                .constrainAs(dateRow) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(calendar.bottom)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Divider(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp),
                color = Color.LightGray
            )

            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${selectedDate.dayOfMonth} ${
                        selectedDate.month.getDisplayName(
                            TextStyle.SHORT,
                            Locale.getDefault()
                        )
                    }",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.LightGray
                )
            }

            Divider(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp),
                color = Color.LightGray
            )
        }


        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val formattedTime = selectedEventTime.value?.format(formatter) ?: ""
        val formattedTimeNow = LocalTime.now().format(formatter) ?: ""

            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .height(60.dp)
                    .constrainAs(eventText) {
                        top.linkTo(dateRow.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                colors = CardDefaults.cardColors(Color(0xFFFFFBFE))
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = selectedEventTitle.value ?: "",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .align(Alignment.CenterStart)
                    )
                    if(formattedTime != formattedTimeNow) {
                        Text(
                            text = formattedTime,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .align(Alignment.CenterEnd)
                        )
                    }
                }
            }


        FloatingActionButton(
            modifier = Modifier
                .padding(bottom = 30.dp, start = 20.dp)
                .constrainAs(addButton) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                },
            onClick = {
                addDialogIsOpen = true
            },
            containerColor = Color(0xFF974BA7)
        ) {
            Icon(
                Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.add_button),
            )
        }

        FloatingActionButton(
            modifier = Modifier
                .padding(bottom = 30.dp, end = 20.dp)
                .constrainAs(goToListButton) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
            onClick = {
                navController.navigate(Screen.ListOfEvents.route)
            },
            containerColor = Color(0xFF974BA7)
        ) {
            Icon(
                Icons.Filled.FormatListBulleted,
                contentDescription = stringResource(id = R.string.go_to_list_of_events),
            )
        }
    }



    AddEventDialog(
        dialogIsOpen = addDialogIsOpen,
        dialogOpen = { isOpen ->
            addDialogIsOpen = isOpen
        },
        context = LocalContext.current
    )
}

@Preview
@Composable
fun CalendarPreview() {
    val navController = rememberNavController()
    HealthyPawsTheme {
        CalendarScreen(navController)
    }
}