package com.stoffe.quitsnus.userinfo

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoffe.quitsnus.USERINFO_ID
import com.stoffe.quitsnus.common.idFromParameter
import com.stoffe.quitsnus.data.UserInfo
import com.stoffe.quitsnus.misc.AccountService
import com.stoffe.quitsnus.misc.StorageService
import com.stoffe.quitsnus.ui.composable.SnackbarManager
import com.stoffe.quitsnus.ui.composable.SnackbarMessage.Companion.toSnackbarMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val storageService: StorageService,
    savedStateHandle: SavedStateHandle,

    ) : ViewModel() {
    val userInfo = mutableStateOf(UserInfo())
    val isInfoExist = mutableStateOf(false)

    init {
        val taskId = savedStateHandle.get<String>(USERINFO_ID)
        if (taskId != null) {
            Log.d("destoffe", " trying to get: " + taskId.idFromParameter())
            viewModelScope.launch(
                CoroutineExceptionHandler { _, throwable ->
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                },
                block = {
                    val user = storageService.getUserInfo(taskId.idFromParameter()) ?: UserInfo()

                    userInfo.value = user
                    isInfoExist.value =
                        user.prillorPerDosa.isNotEmpty() || user.dosorPerDag.isNotEmpty() || user.prisPerDosa.isNotEmpty()
                }
            )
        }
    }

    fun onDoserPerDagChange(newValue: String) {
        userInfo.value = userInfo.value.copy(dosorPerDag = newValue)
    }

    fun onPrillorPerDosaChange(newValue: String) {
        userInfo.value = userInfo.value.copy(prillorPerDosa = newValue)
    }

    fun onPrisPerDosaPerDosaChange(newValue: String) {
        userInfo.value = userInfo.value.copy(prisPerDosa = newValue)
    }

    fun onSaveInClick(popUpScreen: () -> Unit,shouldPop: Boolean) {

        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                SnackbarManager.showMessage(throwable.toSnackbarMessage())
            },
            block = {
                val editedUserInfo = userInfo.value
                if (editedUserInfo.id == null) {
                    storageService.save(editedUserInfo)
                } else {
                    storageService.update(editedUserInfo)
                }
                if(shouldPop){
                    popUpScreen()
                }
            }
        )
    }
}