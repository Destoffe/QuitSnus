package com.stoffe.quitsnus.ui.composable

import android.content.res.Resources


sealed class SnackbarMessage {
        class StringSnackbar(val message: String) : SnackbarMessage()

        companion object {
            fun SnackbarMessage.toMessage(resources: Resources): String {
                return when (this) {
                    is StringSnackbar -> this.message
                }
            }

            fun Throwable.toSnackbarMessage(): SnackbarMessage {
                val message = this.message.orEmpty()
                return StringSnackbar(message)

            }
        }
}