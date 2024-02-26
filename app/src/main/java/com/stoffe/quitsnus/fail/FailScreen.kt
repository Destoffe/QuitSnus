@file:OptIn(ExperimentalMaterial3Api::class)

package com.stoffe.quitsnus.fail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.stoffe.quitsnus.ui.composable.MyDatePickerDialog
import com.stoffe.quitsnus.ui.composable.SnusTimePicker

@Composable
fun FailScreen(
    popup: () -> Unit,
) {
    var date by remember {
        mutableStateOf("Open date picker dialog")
    }

    var time by remember {
        mutableStateOf("Open time picker dialog")
    }

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    var showTimePicker by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Logga prilla") },
                navigationIcon = {
                    IconButton(onClick = { popup() }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close page")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Button(onClick = { showDatePicker = true }) {
                Text(text = date)
            }

            Button(onClick = { showTimePicker = true }) {
                Text(text = time)
            }
            Button(onClick = { showTimePicker = true }) {
                Text(text = "Save")
            }
            if (showTimePicker) {
                SnusTimePicker(
                    onDismiss = { timeString ->
                        showTimePicker = false
                        time = timeString
                    },
                    onSave = { timeString -> time = timeString }
                )
            }

            if (showDatePicker) {
                MyDatePickerDialog(
                    onDateSelected = { date = it },
                    onDismiss = { showDatePicker = false }
                )
            }
        }
    }

}