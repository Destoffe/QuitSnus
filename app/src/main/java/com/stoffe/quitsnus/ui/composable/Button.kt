package com.stoffe.quitsnus.ui.composable

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun BasicButton(
    text: String,
    modifier: Modifier = Modifier,
    action: () -> Unit
    ){
    Button(
        onClick = action,
        modifier = modifier,
    ){
        Text(text = text, fontSize = 16.sp)
    }

}