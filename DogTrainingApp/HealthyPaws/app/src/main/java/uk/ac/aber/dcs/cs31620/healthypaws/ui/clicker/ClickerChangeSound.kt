package uk.ac.aber.dcs.cs31620.healthypaws.ui.clicker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import uk.ac.aber.dcs.cs31620.healthypaws.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClickerChangeSound(
    onWhistleSoundSelected: (Int) -> Unit,
    onSelectedWhistleSoundChanged: (Int) -> Unit,
    dialogIsOpen: Boolean,
    dialogOpen: (Boolean) -> Unit = {},
    selectedWhistleSound: Int,

) {
    val whistleSounds = listOf(
        R.raw.whistle_sound1,
        R.raw.whistle_sound2,
        R.raw.whistle_sound3,
        R.raw.whistle_sound4,
        R.raw.whistle_sound5,
        R.raw.whistle_sound6
    )

    if (dialogIsOpen) {
        Dialog(
            onDismissRequest = {
                dialogOpen(false)
            }
        ) {
            Surface(
                modifier = Modifier
                    .size(450.dp),
                shape = RoundedCornerShape(
                    topStart = 50.dp,
                    topEnd = 50.dp,
                    bottomStart = 50.dp,
                    bottomEnd = 50.dp
                ),
                border = BorderStroke(width = 2.dp, Color(0xFF00000D)),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Select a Whistle Sound",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    whistleSounds.forEach { whistleSound ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = whistleSound == selectedWhistleSound,
                                onClick = { onSelectedWhistleSoundChanged(whistleSound) },
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                text = "Whistle Sound ${whistleSounds.indexOf(whistleSound) + 1}",
                                modifier = Modifier.clickable {
                                    onSelectedWhistleSoundChanged(whistleSound)
                                }
                            )
                        }
                    }
                    Button(
                        onClick = {
                            onWhistleSoundSelected(selectedWhistleSound)
                            dialogOpen(false)
                                  },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}