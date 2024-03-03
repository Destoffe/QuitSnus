package com.stoffe.quitsnus.settings


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.stoffe.quitsnus.ui.composable.BasicButton
import com.stoffe.quitsnus.ui.composable.BasicToolBar

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    restartApp: (String) -> Unit,
    viewModel: SettingsViewModel = hiltViewModel<SettingsViewModel>(),
    popup: () -> Unit,

    ) {

    SettingsScreenContent(
        onSignOutClick = { viewModel.onSignOut(restartApp) },
        popup = popup
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    onSignOutClick: () -> Unit,
    popup: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Inställningar") },
                navigationIcon = {
                    IconButton(onClick = { popup() }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close page")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BasicButton(
                text = "Sign out",
                enabled = true,
                action = {
                    onSignOutClick()
                }
            )
        }
    }
}