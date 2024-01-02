package com.stoffe.quitsnus.userinfo

import com.stoffe.quitsnus.ui.composable.SnackbarManager

data class UserInfoUiState(
    val doserPerDag: Double = 0.0,
    val prillorPerDosa: Int = 0,
    val prisPerDosa : Double = 0.0,
    val snackbarManager: SnackbarManager = SnackbarManager
)