package com.stoffe.quitsnus.ui.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.stoffe.quitsnus.ui.theme.QuitSnusTheme

@Composable
fun EmailField(
    value: String,
    onNewValue:  (String) -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = {onNewValue(it)},
        placeholder = { Text("Email")},
        leadingIcon = {Icon(imageVector = Icons.Default.Email,contentDescription = "Email")}
    )
}

@Composable
@Preview
fun EmailFieldPreview(){
    QuitSnusTheme {
        EmailField(
            value = "Email@Email.com",
            onNewValue = {}
        )
    }
}

