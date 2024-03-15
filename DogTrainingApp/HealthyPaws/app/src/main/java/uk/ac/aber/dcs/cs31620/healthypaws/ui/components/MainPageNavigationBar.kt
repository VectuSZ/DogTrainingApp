package uk.ac.aber.dcs.cs31620.healthypaws.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.aber.dcs.cs31620.healthypaws.ui.navigation.Screen
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.healthypaws.R
import uk.ac.aber.dcs.cs31620.healthypaws.ui.navigation.screens
import uk.ac.aber.dcs.cs31620.healthypaws.ui.theme.HealthyPawsTheme

@Composable
fun MainPageNavigationBar(
    navController: NavController
) {
    val icons = mapOf(
        Screen.Training to IconGroup(
            filledIcon = Icons.Filled.Pets,
            outlinedIcon = Icons.Outlined.Pets,
            label = stringResource(id = R.string.training)
        ),
        Screen.Profile to IconGroup(
            filledIcon = Icons.Filled.Loyalty,
            outlinedIcon = Icons.Outlined.Loyalty,
            label = stringResource(id = R.string.dog_profile)
        ),
        Screen.Events to IconGroup(
            filledIcon = Icons.Filled.CalendarMonth,
            outlinedIcon = Icons.Outlined.CalendarMonth,
            label = stringResource(id = R.string.events)
        ),
        Screen.DietHome to IconGroup(
            filledIcon = Icons.Filled.MenuBook,
            outlinedIcon = Icons.Outlined.MenuBook,
            label = stringResource(id = R.string.diet)
        )
    )

    NavigationBar(
        containerColor = Color(0xFFFAEFFC),
        modifier = Modifier
            .graphicsLayer {
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp
                )
                clip = true
            }
            ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDesitination = navBackStackEntry?.destination
        screens.forEach { screen ->
            val isSelected = currentDesitination?.hierarchy?.any { it.route == screen.route } == true
            val labelText = icons[screen]!!.label
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = (if (isSelected)
                            icons[screen]!!.filledIcon
                        else
                            icons[screen]!!.outlinedIcon),
                        contentDescription = labelText,
                        tint = Color(0xFF974BA7)
                    )
                },
                label = { Text(labelText) },
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun MainPageNavigationBarPreview() {
    val navController = rememberNavController()
    HealthyPawsTheme {
        MainPageNavigationBar(navController)
    }
}