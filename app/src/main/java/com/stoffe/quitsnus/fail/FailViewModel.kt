package com.stoffe.quitsnus.fail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoffe.quitsnus.data.UserInfo
import com.stoffe.quitsnus.misc.StorageService
import com.stoffe.quitsnus.ui.composable.SnackbarManager
import com.stoffe.quitsnus.ui.composable.SnackbarMessage.Companion.toSnackbarMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class FailViewModel @Inject constructor(
    private val storageService: StorageService,

    ) : ViewModel() {
    private val userInfo = mutableStateOf(UserInfo())

    init {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                SnackbarManager.showMessage(throwable.toSnackbarMessage())
            },
            block = {
                storageService.userInfo.collect { userInfoValue ->
                    userInfo.value = userInfoValue[0]
                }
            }
        )
    }

    fun onSaveInClick(popUpScreen: () -> Unit, date: Date) {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                SnackbarManager.showMessage(throwable.toSnackbarMessage())
            },
            block = {

                val fails = userInfo.value.fails
                fails.add(date)
                val editedUserInfo = userInfo.value.copy(fails = fails)
                storageService.update(editedUserInfo)
                popUpScreen()
            }
        )
    }
}