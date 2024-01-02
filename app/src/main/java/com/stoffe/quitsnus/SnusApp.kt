package com.stoffe.quitsnus

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stoffe.quitsnus.dashboard.DashboardScreen
import com.stoffe.quitsnus.login.LoginScreen
import com.stoffe.quitsnus.settings.SettingsScreen
import com.stoffe.quitsnus.ui.theme.QuitSnusTheme
import com.stoffe.quitsnus.userinfo.UserInfoScreen


@Composable
fun SnusApp() {
    val snackbarHostState = remember { SnackbarHostState() }

    val appState = rememberAppState(snackbarHostState = snackbarHostState)

    QuitSnusTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = appState.snackBarHostState,
                        modifier = Modifier.padding(8.dp),
                        snackbar = {
                            Snackbar(snackbarData = it)
                        }
                    )
                },
                ) {
                NavHost(
                    navController = appState.navController,
                    startDestination = LOGIN_SCREEN,
                    modifier = Modifier.padding(it)
                ) {
                    quitSnusGraph(appState)
                }
            }
        }
    }
}

fun NavGraphBuilder.quitSnusGraph(appState: SnusAppState) {
    composable(LOGIN_SCREEN) {
        LoginScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(DASHBOARD_SCREEN) {
        DashboardScreen(openScreen = { route -> appState.navigate(route) })
    }

    composable(SETTINGS_SCREEN) {
        SettingsScreen(restartApp = { route -> appState.clearAndNavigate(route) })
    }

    composable(USER_INFO_SCREEN) {
        UserInfoScreen(popUpScreen = { appState.popUp() })
    }
}