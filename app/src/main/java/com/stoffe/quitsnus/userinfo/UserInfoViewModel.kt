package com.stoffe.quitsnus.userinfo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoffe.quitsnus.DASHBOARD_SCREEN
import com.stoffe.quitsnus.LOGIN_SCREEN
import com.stoffe.quitsnus.common.isValidEmail
import com.stoffe.quitsnus.login.LoginUiState
import com.stoffe.quitsnus.misc.AccountService
import com.stoffe.quitsnus.ui.composable.SnackbarManager
import com.stoffe.quitsnus.ui.composable.SnackbarMessage
import com.stoffe.quitsnus.ui.composable.SnackbarMessage.Companion.toSnackbarMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val accountService: AccountService

) : ViewModel() {
    var uiState = mutableStateOf(UserInfoUiState())
        private set

    private val doserPerDag
        get() = uiState.value.doserPerDag

    private val prillorPerDosa
        get() = uiState.value.prillorPerDosa

    private val prisPerDosa
        get() = uiState.value.prisPerDosa

    fun onDoserPerDagChange(newValue: String) {
        uiState.value = uiState.value.copy(doserPerDag = newValue.toDouble())
    }

    fun onPrillorPerDosaChange(newValue: String) {
        uiState.value = uiState.value.copy(prillorPerDosa = newValue.toInt())
    }

    fun onPrisPerDosaPerDosaChange(newValue: String) {
        uiState.value = uiState.value.copy(prisPerDosa = newValue.toDouble())
    }

    fun onSaveInClick(openAndPopUp: (String, String) -> Unit){

        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                SnackbarManager.showMessage(throwable.toSnackbarMessage())
            },
            block = {

            }
        )

    }
}