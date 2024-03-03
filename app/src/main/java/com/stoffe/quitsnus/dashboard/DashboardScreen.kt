@file:OptIn(ExperimentalMaterial3Api::class)

package com.stoffe.quitsnus.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.stoffe.quitsnus.R
import com.stoffe.quitsnus.common.Calculations
import com.stoffe.quitsnus.common.Calculations.getLastDateSnused
import com.stoffe.quitsnus.data.UserInfo
import com.stoffe.quitsnus.ui.composable.ActionToolBar
import com.stoffe.quitsnus.ui.composable.BasicButton
import com.stoffe.quitsnus.ui.composable.BasicCard
import com.stoffe.quitsnus.ui.composable.CardText
import com.stoffe.quitsnus.ui.composable.CardTextTitle
import com.stoffe.quitsnus.ui.composable.SnusHeaderText1


@Composable
fun DashboardScreen(
    openAndPopUpFail: () -> Unit,
    openSettingsScreen: () -> Unit,
    openUserInfoScreen: (String) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel<DashboardViewModel>()
) {
    val userInfos by viewModel
        .userInfo
        .collectAsState(null)

    val loading = viewModel.loading.collectAsState()
    val pricePerDaySaved by viewModel.pricePerDaySaved.collectAsState(initial = 0.0)
    val daysSinceUsed by viewModel.daysSinceUsed.collectAsState(initial = 0)
    val snusNotSnused by viewModel.snusNotSnused.collectAsState(initial = 0)

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
                    pricePerDaySaved = pricePerDaySaved,
                    daysSinceUsed = daysSinceUsed,
                    snusNotSnused = snusNotSnused,
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
    pricePerDaySaved: Double,
    daysSinceUsed: Long,
    snusNotSnused: Int,
) {

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

        SnusHeaderText1(
            text = "Du har inte snusat på $daysSinceUsed dagar, Grattis!"
        )
        BasicCard {
            CardText(text = "Du hade egentligen snusat $snusNotSnused prillor")
            CardText(text = "Detta har sparat dig $moneySaved :-")
        }

        BasicCard(
            onClick = {
                openUserInfoScreen(openAndNavigateUserInfoScreen, userInfo)
            }
        ) {
            CardText(text = "Dosor per dag " + userInfo.dosorPerDag)
            CardText(text = "Prillor per dosa " + userInfo.prillorPerDosa)
            CardText(text = "Pris per dosa " + userInfo.prisPerDosa)
        }

        BasicCard {
            CardTextTitle(text = "Såhär mycket sparar du")
            CardText(text = "${pricePerDaySaved.toInt()}:- Per dag")
            CardText(text = (pricePerDaySaved * 7).toInt().toString() + ":- Per vecka")
            CardText(text = (pricePerDaySaved * 31).toInt().toString() + ":- Per månad")
            CardText(text = (pricePerDaySaved * 365).toInt().toString() + ":- Per år")
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