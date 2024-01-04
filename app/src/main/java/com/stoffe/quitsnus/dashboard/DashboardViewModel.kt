package com.stoffe.quitsnus.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoffe.quitsnus.USERINFO_ID
import com.stoffe.quitsnus.USERINFO_ID_ARG
import com.stoffe.quitsnus.USER_INFO_SCREEN
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
class DashboardViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService

) : ViewModel() {
    val userInfo = MutableStateFlow<UserInfo?>(null)
    val loading = MutableStateFlow(true)
    val shouldNavigateToDataInput = MutableStateFlow(false)

    init {
        loading.value = true
        getUserData()
    }

    fun onUserInfoActionClick(openScreen: (String) -> Unit, userInfo: UserInfo) {
        openScreen("$USER_INFO_SCREEN?$USERINFO_ID={${userInfo.id}}")
        Log.d("destoffe","My id: "+ accountService.currentUserId )
    }

    fun getUserData(){
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                SnackbarManager.showMessage(throwable.toSnackbarMessage())
                loading.value = false
                shouldNavigateToDataInput.value = true
            },
            block = {
                storageService.userInfo.collect { userInfoValue ->
                    userInfo.value = userInfoValue[0]
                    if(userInfoValue[0] == null){
                        shouldNavigateToDataInput.value = true
                    }else {
                        shouldNavigateToDataInput.value = false
                    }

                    loading.value = false
                }
            }
        )

    }
}