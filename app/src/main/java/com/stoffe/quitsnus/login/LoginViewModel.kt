package com.stoffe.quitsnus.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoffe.quitsnus.DASHBOARD_SCREEN
import com.stoffe.quitsnus.LOGIN_SCREEN
import com.stoffe.quitsnus.common.isValidEmail
import com.stoffe.quitsnus.data.LoginUiState
import com.stoffe.quitsnus.misc.AccountService
import com.stoffe.quitsnus.ui.composable.SnackbarManager
import com.stoffe.quitsnus.ui.composable.SnackbarMessage.Companion.toSnackbarMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService

) : ViewModel() {
    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email
        get() = uiState.value.email

    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit){
        if(!email.isValidEmail()){
            // Show snackbar
            return
        }

        if(password.isBlank()){
            // Show snackbar
            return
        }

        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                SnackbarManager.showMessage(throwable.toSnackbarMessage())
            },
            block = {
                accountService.authenticate(email = email,password = password)
                openAndPopUp(DASHBOARD_SCREEN, LOGIN_SCREEN)
            }
        )

    }
}