package com.stoffe.quitsnus

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.stoffe.quitsnus.dashboard.DashboardScreen
import com.stoffe.quitsnus.fail.FailScreen
import com.stoffe.quitsnus.login.LoginScreen
import com.stoffe.quitsnus.settings.SettingsScreen
import com.stoffe.quitsnus.ui.theme.QuitSnusTheme
import com.stoffe.quitsnus.userinfo.UserInfoScreen


@Composable
fun SnusApp(
    viewModel: MainViewModel = hiltViewModel<MainViewModel>()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val appState = rememberAppState(snackbarHostState = snackbarHostState)
    val hasUser = viewModel.hasUser
    val userInfo by viewModel.hasData.collectAsState(initial = null)

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
                    val startDestination = if(!hasUser) {
                        LOGIN_SCREEN
                    } else if(userInfo?.isEmpty() == true){
                        USER_INFO_SCREEN
                    } else {
                        DASHBOARD_SCREEN
                    }
                    NavHost(
                        navController = appState.navController,
                        startDestination = startDestination,
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
        DashboardScreen(
            openAndPopUpInit = {
                appState.navigateAndPopUp(
                    it,
                    DASHBOARD_SCREEN
                )
            },
            openSettingsScreen = { appState.navigate(SETTINGS_SCREEN) },
            openAndPopUpFail = {
                appState.navigate(FAIL_SCREEN)
            },
            openUserInfoScreen = {
                appState.navigate(it)
            }
        )
    }

    composable(SETTINGS_SCREEN) {
        SettingsScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            popup = { appState.popUp() }
        )
    }

    composable(
        route = "$USER_INFO_SCREEN$USERINFO_ID_ARG",
        arguments = listOf(navArgument(USERINFO_ID) {
            nullable = true
            defaultValue = null
        })
    ) {
        UserInfoScreen(popUpScreen = {
            appState.popUp()
        },
            isBackStackEmpty = appState.navController.previousBackStackEntry == null
        )
    }

    composable(FAIL_SCREEN) {
        FailScreen(popup = { appState.popUp() })
    }
}