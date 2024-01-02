package com.stoffe.quitsnus.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoffe.quitsnus.DASHBOARD_SCREEN
import com.stoffe.quitsnus.LOGIN_SCREEN
import com.stoffe.quitsnus.common.isValidEmail
import com.stoffe.quitsnus.data.LoginUiState
import com.stoffe.quitsnus.misc.AccountService
import com.stoffe.quitsnus.ui.composable.SnackbarManager
import com.stoffe.quitsnus.ui.composable.SnackbarMessage
import com.stoffe.quitsnus.ui.composable.SnackbarMessage.Companion.toSnackbarMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val accountService: AccountService

) : ViewModel() {

    fun onSignOut(restartApp: (String) -> Unit){

        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                SnackbarManager.showMessage(throwable.toSnackbarMessage())
            },
            block = {
                accountService.signOut()
                restartApp(LOGIN_SCREEN)
            }
        )

    }
}