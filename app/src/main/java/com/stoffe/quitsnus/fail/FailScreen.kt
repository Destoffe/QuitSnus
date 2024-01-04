@file:OptIn(ExperimentalMaterial3Api::class)

package com.stoffe.quitsnus.fail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.stoffe.quitsnus.ui.composable.MyDatePickerDialog
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun FailScreen(
    openAndPopUp: () -> Unit,
) {
    var date by remember {
        mutableStateOf("Open date picker dialog")
    }

    var showDatePicker by remember {
        mutableStateOf(false)
    }
    Column {

        Box(contentAlignment = Alignment.Center) {
            Button(onClick = { showDatePicker = true }) {
                Text(text = date)
            }
        }

        if (showDatePicker) {
            MyDatePickerDialog(
                onDateSelected = { date = it },
                onDismiss = { showDatePicker = false }
            )
        }
    }
}