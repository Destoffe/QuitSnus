package com.stoffe.quitsnus.settings


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
    viewModel: SettingsViewModel = hiltViewModel<SettingsViewModel>()
) {

    SettingsScreenContent(
        onSignOutClick = {viewModel.onSignOut(restartApp)}
    )

}

@Composable
fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    onSignOutClick: () -> Unit,
){
    BasicToolBar(title = "Login")
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BasicButton(text = "Sign out") {
            onSignOutClick()
        }
    }
}