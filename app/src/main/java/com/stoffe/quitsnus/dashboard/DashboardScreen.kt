package com.stoffe.quitsnus.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.stoffe.quitsnus.R
import com.stoffe.quitsnus.SETTINGS_SCREEN
import com.stoffe.quitsnus.ui.composable.ActionToolBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    openScreen: (String) -> Unit,
){

    Scaffold {
        Column {
            ActionToolBar(
                modifier = Modifier.wrapContentSize(Alignment.TopEnd),
                title = "Quit Snus",
                endActionIcon = R.drawable.ic_settings_24,
                endAction = {openScreen(SETTINGS_SCREEN) }
            )

            Text(text = "Logged in")
        }

    }

}