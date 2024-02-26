package com.stoffe.quitsnus.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stoffe.quitsnus.common.Utils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnusTimePicker(
    onDismiss: (String) -> Unit,
    onSave: (String) -> Unit
) {
    var selectedHour by remember { mutableIntStateOf(0) }
    var selectedMinute by remember { mutableIntStateOf(0) }
    val time by remember(selectedHour,selectedMinute) {
        mutableStateOf(Utils.formatTime(selectedHour,selectedMinute))
    }

    val timeState = rememberTimePickerState(
        initialHour = selectedHour,
        initialMinute = selectedMinute,
        is24Hour = true
    )

    AlertDialog(onDismissRequest = {
        selectedHour = timeState.hour
        selectedMinute = timeState.minute
        onDismiss(time)
    }) {
        Surface(
            color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                TimePicker(state = timeState)
                Button(onClick = {
                    selectedHour = timeState.hour
                    selectedMinute = timeState.minute
                    onSave(time)
                }) {
                    Text(text = "Spara")
                }
            }
        }
    }
}