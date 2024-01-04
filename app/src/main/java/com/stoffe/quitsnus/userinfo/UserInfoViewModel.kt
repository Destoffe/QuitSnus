package com.stoffe.quitsnus.userinfo

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoffe.quitsnus.data.UserInfo
import com.stoffe.quitsnus.misc.AccountService
import com.stoffe.quitsnus.misc.StorageService
import com.stoffe.quitsnus.ui.composable.SnackbarManager
import com.stoffe.quitsnus.ui.composable.SnackbarMessage.Companion.toSnackbarMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService

) : ViewModel() {

    val userInfo = mutableStateOf(UserInfo())
    var uiState = mutableStateOf(UserInfoUiState())
        private set

    private val doserPerDag
        get() = uiState.value.userInfo.doserPerDag

    private val prillorPerDosa
        get() = uiState.value.userInfo.prillorPerDosa

    private val prisPerDosa
        get() = uiState.value.userInfo.prisPerDosa

    fun onDoserPerDagChange(newValue: String) {
        userInfo.value = userInfo.value.copy(doserPerDag = newValue)
    }

    fun onPrillorPerDosaChange(newValue: String) {
        userInfo.value = userInfo.value.copy(prillorPerDosa = newValue)
    }

    fun onPrisPerDosaPerDosaChange(newValue: String) {
        userInfo.value = userInfo.value.copy(prisPerDosa = newValue)
    }

    fun onSaveInClick(popUpScreen: () -> Unit){

        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                SnackbarManager.showMessage(throwable.toSnackbarMessage())
            },
            block = {
                val editedUserInfo = userInfo.value
                if (editedUserInfo.id == null) {
                    val test = storageService.save(editedUserInfo)
                    if(test.isNotBlank()){
                        popUpScreen()
                    }
                    Log.d("destoffe",": " + test)
                } else {
                    storageService.update(editedUserInfo)
                }

            }
        )

    }
}