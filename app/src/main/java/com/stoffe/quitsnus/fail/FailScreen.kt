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
import androidx.hilt.navigation.compose.hiltViewModel
import com.stoffe.quitsnus.common.Utils.isValidDate
import com.stoffe.quitsnus.ui.composable.DatePickerEditField
import com.stoffe.quitsnus.ui.composable.MyDatePickerDialog
import com.stoffe.quitsnus.ui.composable.SnusHeaderText1
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun FailScreen(
    popup: () -> Unit,
    viewModel: FailViewModel = hiltViewModel<FailViewModel>()
) {
    var date by remember {
        mutableStateOf("Klicka för att välja datum")
    }

    var showDatePicker by remember {
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            SnusHeaderText1(text = "När snusade du?")

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            DatePickerEditField(
                value = date,
                onNewValue = {},
                label = "Välj datum",
                placeHolder = Date().toString()
            ) {
                showDatePicker = true
            }
            Button(
                onClick = {
                    val newDate = dateFormat.parse(date)
                    if (newDate != null) {
                        viewModel.onSaveInClick(popup, newDate)
                    }
                },
                enabled = isValidDate(date)
            ) {
                Text(text = "Save")
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