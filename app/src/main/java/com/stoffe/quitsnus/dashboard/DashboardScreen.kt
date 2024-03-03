@file:OptIn(ExperimentalMaterial3Api::class)

package com.stoffe.quitsnus.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stoffe.quitsnus.R
import com.stoffe.quitsnus.USER_INFO_SCREEN
import com.stoffe.quitsnus.common.Calculations
import com.stoffe.quitsnus.common.Calculations.getLastDateSnused
import com.stoffe.quitsnus.data.UserInfo
import com.stoffe.quitsnus.ui.composable.ActionToolBar
import com.stoffe.quitsnus.ui.composable.BasicButton


@Composable
fun DashboardScreen(
    openAndPopUpInit: (String) -> Unit,
    openAndPopUpFail: () -> Unit,
    openSettingsScreen: () -> Unit,
    openUserInfoScreen: (String) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel<DashboardViewModel>()
) {
    val userInfos by viewModel
        .userInfo
        .collectAsState(null)

    val loading = viewModel.loading.collectAsState()

    when (loading.value) {
        true -> DashboardLoadingScreen()
        (false) ->
            if (userInfos != null) {
                DashboardScreenContent(
                    userInfo = userInfos!!,
                    openScreen = openSettingsScreen,
                    openAndPopFail = openAndPopUpFail,
                    openUserInfoScreen = viewModel::onUserInfoActionClick,
                    openAndNavigateUserInfoScreen = openUserInfoScreen,
                )
            }
    }
}

@Composable
fun DashboardScreenContent(
    userInfo: UserInfo,
    openScreen: () -> Unit,
    openAndPopFail: () -> Unit,
    openUserInfoScreen: ((String) -> Unit, UserInfo) -> Unit,
    openAndNavigateUserInfoScreen: (String) -> Unit,
) {
    var pricePerDaySaved = 0.0
    if (userInfo.dosorPerDag.isNotEmpty() && userInfo.prisPerDosa.isNotEmpty()) {
        pricePerDaySaved = Calculations.calculateMoneySavedPerDay(
            packagesPerDay = userInfo.dosorPerDag.toDouble(),
            packageCost = userInfo.prisPerDosa.toDouble()
        )
    }

    val daysSinceUsed = Calculations.calculateDateDifference(getLastDateSnused(userInfo.fails,userInfo.createdAt).toString())

    val snusNotSnused = Calculations.calculateSnusedNotUsed(
        userInfo.prillorPerDosa.toInt(),
        userInfo.dosorPerDag.toDouble(),
        daysSinceUsed.toInt()
    )

    val moneySaved = (daysSinceUsed * pricePerDaySaved).toInt()

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        ActionToolBar(
            modifier = Modifier.wrapContentSize(Alignment.TopEnd),
            title = "Quit Snus",
            endActionIcon = R.drawable.ic_settings_24,
            endAction = { openScreen() }
        )
        Text(
            text = "Du har inte snusat på $daysSinceUsed dagar, Grattis!",
            fontWeight = FontWeight.Bold,
            fontSize = 34.sp,
            lineHeight = TextUnit(34f, TextUnitType.Sp),
            textAlign = TextAlign.Center
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),

            ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Du hade egentligen snusat $snusNotSnused prillor")
                Text(text = "Detta har sparat dig $moneySaved :-")
            }

        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = {
                openUserInfoScreen(openAndNavigateUserInfoScreen, userInfo)
            }

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
                .padding(horizontal = 16.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Såhär mycket sparar du",
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "${pricePerDaySaved.toInt()}:- Per dag",
                    textAlign = TextAlign.Center
                )
                Text(
                    text = (pricePerDaySaved * 7).toInt().toString() + ":- Per vecka",
                    textAlign = TextAlign.Center
                )

                Text(
                    text = (pricePerDaySaved * 31).toInt().toString() + ":- Per månad",
                    textAlign = TextAlign.Center
                )

                Text(
                    text = (pricePerDaySaved * 365).toInt().toString() + ":- Per år",
                    textAlign = TextAlign.Center
                )
            }

        }

        BasicButton(
            text = "Har du snusat igen?",
            action = {
                openAndPopFail()
            }
        )
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