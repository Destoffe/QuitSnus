package com.stoffe.quitsnus

import androidx.lifecycle.ViewModel
import com.stoffe.quitsnus.misc.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    accountService: AccountService

) : ViewModel() {

    val hasUser = accountService.hasUser
}