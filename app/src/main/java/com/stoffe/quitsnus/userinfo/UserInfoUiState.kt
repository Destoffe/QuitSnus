package com.stoffe.quitsnus.userinfo

import com.stoffe.quitsnus.data.UserInfo
import com.stoffe.quitsnus.ui.composable.SnackbarManager

data class UserInfoUiState(
    val userInfo: UserInfo = UserInfo(),
    val snackbarManager: SnackbarManager = SnackbarManager
)