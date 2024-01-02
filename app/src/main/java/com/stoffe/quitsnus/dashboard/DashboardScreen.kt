package com.stoffe.quitsnus.dashboard

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DashboardScreen(
    openScreen: (String) -> Unit,
){
    Text(text = "Logged in")
}