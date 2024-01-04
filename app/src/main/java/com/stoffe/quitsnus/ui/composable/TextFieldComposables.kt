package com.stoffe.quitsnus.ui.composable

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stoffe.quitsnus.R
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
 fun PasswordField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isVisible by remember { mutableStateOf(false) }

    val icon =
        if (isVisible) painterResource(id = R.drawable.ic_visibility_24)
        else painterResource(id = R.drawable.ic_visibility_off_24)

    val visualTransformation =
        if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {onNewValue(it)},
        placeholder = { Text(text = "Password")},
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Password")},
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(painter = icon, contentDescription = "Visibility")
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation

    )
        
}

@Composable
fun NumberEditField(
    value: String,
    onNewValue:  (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    placeHolder: String,
){
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        label = { Text(text = label)},
        onValueChange = {onNewValue(it)},
        placeholder = { Text(placeHolder)},
        leadingIcon = {Icon(painter = painterResource(id = R.drawable.prilla),contentDescription = "Email",modifier = Modifier.size(24.dp)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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

