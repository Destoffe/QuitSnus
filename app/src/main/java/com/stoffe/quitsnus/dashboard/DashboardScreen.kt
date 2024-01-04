package com.stoffe.quitsnus.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stoffe.quitsnus.R
import com.stoffe.quitsnus.SETTINGS_SCREEN
import com.stoffe.quitsnus.USER_INFO_SCREEN
import com.stoffe.quitsnus.data.UserInfo
import com.stoffe.quitsnus.ui.composable.ActionToolBar
import com.stoffe.quitsnus.ui.composable.BasicButton


@Composable
fun DashboardScreen(
    openScreen: (String) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel<DashboardViewModel>()
) {
    viewModel.getUserData()
    val userInfos by viewModel
        .userInfo
        .collectAsStateWithLifecycle(null)

    val loading = viewModel.loading.collectAsState()

    if(loading.value){
        DashboardLoadingScreen()
    }else if(!loading.value && userInfos != null){
        DashboardScreenContent(
            userInfo = userInfos!!,
            openScreen = openScreen
        )
    }else {
        openScreen(USER_INFO_SCREEN)
    }
}


@Composable
fun DashboardScreenContent(
    userInfo: UserInfo,
    openScreen: (String) -> Unit
) {
    Scaffold {
        Column(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ActionToolBar(
                modifier = Modifier.wrapContentSize(Alignment.TopEnd),
                title = "Quit Snus",
                endActionIcon = R.drawable.ic_settings_24,
                endAction = { openScreen(SETTINGS_SCREEN) }
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                ) {
                    Text(text = "Doser per dag " + userInfo.doserPerDag)
                    Text(text = "Prillor per dosa " + userInfo.prillorPerDosa)
                    Text(text = "Pris per dosa " + userInfo.prisPerDosa)
                }

            }


            BasicButton(text = "Lets Quit!") {
                openScreen(USER_INFO_SCREEN)
            }
        }

    }
}

@Composable
fun DashboardLoadingScreen(){
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CircularProgressIndicator()
        }
    }
}