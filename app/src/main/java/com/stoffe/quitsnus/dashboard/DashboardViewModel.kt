package com.stoffe.quitsnus.dashboard

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoffe.quitsnus.common.idFromParameter
import com.stoffe.quitsnus.data.UserInfo
import com.stoffe.quitsnus.misc.AccountService
import com.stoffe.quitsnus.misc.StorageService
import com.stoffe.quitsnus.ui.composable.SnackbarManager
import com.stoffe.quitsnus.ui.composable.SnackbarMessage.Companion.toSnackbarMessage
import com.stoffe.quitsnus.userinfo.UserInfoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService

) : ViewModel() {
    val userInfo = storageService.userInfo

    init {
        onSaveInClick()
    }

    fun onSaveInClick(){

        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                SnackbarManager.showMessage(throwable.toSnackbarMessage())
            },
            block = {
                Log.d("destoffe","id: " + accountService.currentUserId.idFromParameter())
                Log.d("destoffe","id: " + accountService.currentUserId)

            }
        )

    }
}