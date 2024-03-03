package com.stoffe.quitsnus.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.stoffe.quitsnus.common.fieldModifier
import com.stoffe.quitsnus.ui.composable.BasicButton
import com.stoffe.quitsnus.ui.composable.BasicToolBar
import com.stoffe.quitsnus.ui.composable.EmailField
import com.stoffe.quitsnus.ui.composable.PasswordField

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    openAndPopUp: (String, String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel<LoginViewModel>()
) {

    val uiState by viewModel.uiState
    val user = viewModel.user

    LoginScreenContent(
        modifier = modifier,
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = { viewModel.onSignInClick() },
        onSignInGuestClick = { viewModel.onSignInGuestClick() }
    )

    if (user) {
        viewModel.changeScreen(openAndPopUp)
    }
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onSignInGuestClick: () -> Unit,
) {
    BasicToolBar(title = "Login")
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        EmailField(
            value = uiState.email,
            onNewValue = onEmailChange,
            modifier = Modifier.fieldModifier()
        )
        PasswordField(
            value = uiState.password,
            onNewValue = onPasswordChange,
            modifier = Modifier.fieldModifier()
        )
        BasicButton(
            text = "Sign in",
            action = {
                onSignInClick()

            }
        )

        BasicButton(
            text = "Use app as Guest",
            action = {
                onSignInGuestClick()
            }
        )
    }
}