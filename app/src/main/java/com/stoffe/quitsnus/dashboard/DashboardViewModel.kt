package com.stoffe.quitsnus.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoffe.quitsnus.USERINFO_ID
import com.stoffe.quitsnus.USERINFO_ID_ARG
import com.stoffe.quitsnus.USER_INFO_SCREEN
import com.stoffe.quitsnus.common.Calculations
import com.stoffe.quitsnus.data.UserInfo
import com.stoffe.quitsnus.misc.AccountService
import com.stoffe.quitsnus.misc.StorageService
import com.stoffe.quitsnus.ui.composable.SnackbarManager
import com.stoffe.quitsnus.ui.composable.SnackbarMessage.Companion.toSnackbarMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService

) : ViewModel() {
    val userInfo = MutableStateFlow<UserInfo?>(null)
    val loading = MutableStateFlow(true)
    val shouldNavigateToDataInput = MutableStateFlow(false)

    val pricePerDaySaved = userInfo.mapLatest {
        if(it == null){
            return@mapLatest 0.0
        }
        Calculations.calculateMoneySavedPerDay(
            packagesPerDay = it.dosorPerDag.toDouble(),
            packageCost = it.prisPerDosa.toDouble()
        )
    }

    val daysSinceUsed = userInfo.mapLatest {
        if(it == null){
            return@mapLatest 0
        }
        Calculations.calculateDateDifference(
            Calculations.getLastDateSnused(
                it.fails,
                it.createdAt
            ).toString())
    }

    val snusNotSnused = combine(userInfo,daysSinceUsed) { userInfo,daysSinceUsed ->
        if(userInfo == null || daysSinceUsed.toInt() == 0){
            return@combine 0
        }
        Calculations.calculateSnusedNotUsed(
            userInfo.prillorPerDosa.toInt(),
            userInfo.dosorPerDag.toDouble(),
            daysSinceUsed.toInt()
        )
    }

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
                    shouldNavigateToDataInput.value = false
                    loading.value = false
                }
            }
        )
    }
}