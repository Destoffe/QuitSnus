@file:OptIn(ExperimentalMaterial3Api::class)

package com.stoffe.quitsnus.ui.composable

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun BasicToolBar(title: String){
    TopAppBar(title = { Text(title) })
}