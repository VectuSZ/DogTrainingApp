package uk.ac.aber.dcs.cs31620.healthypaws.ui.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.healthypaws.R
import uk.ac.aber.dcs.cs31620.healthypaws.model.CalendarEvent
import uk.ac.aber.dcs.cs31620.healthypaws.model.CalendarEventViewModel
import uk.ac.aber.dcs.cs31620.healthypaws.model.Diet
import uk.ac.aber.dcs.cs31620.healthypaws.model.DietViewModel
import uk.ac.aber.dcs.cs31620.healthypaws.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.healthypaws.ui.diet.AddDietDialog
import uk.ac.aber.dcs.cs31620.healthypaws.ui.navigation.Screen
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun ListOfEventsScreen(
    navController: NavHostController,
    calendarEventViewModel: CalendarEventViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val title by remember { mutableStateOf("List of events") }
    val eventList by calendarEventViewModel.eventList.observeAsState(listOf())

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
            ListOfEventsScreenContent(
                modifier = Modifier,
                navController = navController,
                eventList = eventList,
                calendarEventViewModel = calendarEventViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListOfEventsScreenContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    eventList: List<CalendarEvent> = listOf(),
    calendarEventViewModel: CalendarEventViewModel = viewModel()
) {
    val state = rememberLazyListState()
    val sortedEvents = eventList.sortedBy { it.date }

    LazyColumn(
        state = state,
        contentPadding = PaddingValues(bottom = 10.dp),
        modifier = Modifier
            .padding(end = 5.dp, bottom = 10.dp)
    ) {
        items(sortedEvents) {
            Card(
                onClick = {
                },
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp, top = 5.dp)
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(Color(0xFFFFFBFE))
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = it.title,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .align(Alignment.CenterStart)
                    )

                    Text(
                        text = it.date.toString(),
                        fontSize = 12.sp,
                        modifier = Modifier
                            .align(Alignment.Center),
                        color = Color(0xFFCC7DDA)
                    )

                    Text(
                        text = it.time.format(DateTimeFormatter.ofPattern("HH:mm")),
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .align(Alignment.CenterEnd)
                    )

                    if(it.date.isBefore(LocalDate.now())){
                        Text(
                            text = it.title,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .align(Alignment.CenterStart),
                            color = Color.LightGray
                        )

                        Text(
                            text = it.date.toString(),
                            fontSize = 12.sp,
                            modifier = Modifier
                                .align(Alignment.Center),
                            color = Color.LightGray
                        )

                        Text(
                            text = it.time.format(DateTimeFormatter.ofPattern("HH:mm")),
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .align(Alignment.CenterEnd),
                            color = Color.LightGray
                        )
                    }
                }
            }

            Divider()
        }
    }
}
