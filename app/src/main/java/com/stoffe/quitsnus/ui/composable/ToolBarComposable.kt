@file:OptIn(ExperimentalMaterial3Api::class)

package com.stoffe.quitsnus.ui.composable

import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun BasicToolBar(title: String){
    TopAppBar(title = { Text(title) })
}

@Composable
fun ActionToolBar(
    title: String,
    @DrawableRes endActionIcon: Int,
    modifier: Modifier = Modifier,
    endAction: () -> Unit,
    ){
    TopAppBar(
        title = { Text(text = title)},
        actions = {
            Box(modifier){
                IconButton(onClick = endAction) {
                    Icon(painter = painterResource(endActionIcon), contentDescription = "Action")
                }
            }
        }
    )
}