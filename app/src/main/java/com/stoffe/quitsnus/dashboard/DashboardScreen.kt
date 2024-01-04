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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stoffe.quitsnus.R
import com.stoffe.quitsnus.common.Calculations
import com.stoffe.quitsnus.data.UserInfo
import com.stoffe.quitsnus.ui.composable.ActionToolBar
import com.stoffe.quitsnus.ui.composable.BasicButton


@Composable
fun DashboardScreen(
    openAndPopUp: () -> Unit,
    openScreen: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel<DashboardViewModel>()
) {
    val userInfos by viewModel
        .userInfo
        .collectAsStateWithLifecycle(null)

    val loading = viewModel.loading.collectAsState()
    val shouldNavigateToInput = viewModel.shouldNavigateToDataInput.collectAsState()

    if (loading.value) {
        DashboardLoadingScreen()
    } else if (!loading.value && userInfos != null) {
        DashboardScreenContent(
            userInfo = userInfos!!,
            openAndPop = openAndPopUp,
            openScreen = openScreen
        )
    } else if(!loading.value && shouldNavigateToInput.value) {
        openAndPopUp()
    }else {
        DashboardLoadingScreen()
    }
}


@Composable
fun DashboardScreenContent(
    userInfo: UserInfo,
    openScreen: () -> Unit,
    openAndPop: () -> Unit,
) {
    Scaffold {

        val pricePerDaySaved = Calculations.calculateMoneySavedPerDay(
            packagesPerDay = userInfo.dosorPerDag.toDouble(),
            packageCost = userInfo.prisPerDosa.toDouble())
        Column(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ActionToolBar(
                modifier = Modifier.wrapContentSize(Alignment.TopEnd),
                title = "Quit Snus",
                endActionIcon = R.drawable.ic_settings_24,
                endAction = { openScreen() }
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Dosor per dag " + userInfo.dosorPerDag)
                    Text(text = "Prillor per dosa " + userInfo.prillorPerDosa)
                    Text(text = "Pris per dosa " + userInfo.prisPerDosa)
                }

            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Du sparar: $pricePerDaySaved:- Per dag",
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Du sparar: "+ (pricePerDaySaved * 7) +":- Per vecka",
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Du sparar: "+ (pricePerDaySaved * 31) +":- Per månad",
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Du sparar: "+ (pricePerDaySaved * 365) +":- Per år",
                        textAlign = TextAlign.Center
                    )
                }

            }

            BasicButton(text = "Har du snusat igen?") {
                openAndPop()
            }
        }

    }
}

@Composable
fun DashboardLoadingScreen() {
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