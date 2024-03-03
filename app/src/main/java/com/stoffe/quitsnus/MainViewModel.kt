package com.stoffe.quitsnus

import androidx.lifecycle.ViewModel
import com.stoffe.quitsnus.misc.AccountService
import com.stoffe.quitsnus.misc.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    accountService: AccountService,
    storageService: StorageService

) : ViewModel() {

    val hasUser = accountService.hasUser
    val hasData = storageService.userInfo
}