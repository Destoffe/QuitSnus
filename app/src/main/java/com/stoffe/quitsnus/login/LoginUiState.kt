package com.stoffe.quitsnus.login

import com.stoffe.quitsnus.ui.composable.SnackbarManager

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val snackbarManager: SnackbarManager = SnackbarManager
)
