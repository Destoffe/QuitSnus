package com.stoffe.quitsnus.userinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.stoffe.quitsnus.common.fieldModifier
import com.stoffe.quitsnus.data.UserInfo
import com.stoffe.quitsnus.ui.composable.BasicButton
import com.stoffe.quitsnus.ui.composable.NumberEditField
import com.stoffe.quitsnus.ui.theme.QuitSnusTheme

@Composable
fun UserInfoScreen(
    viewModel: UserInfoViewModel = hiltViewModel<UserInfoViewModel>(),
    popUpScreen: () -> Unit,
    isBackStackEmpty: Boolean = false,
) {
    val uiState by viewModel.userInfo
    val isEmpty by viewModel.isInfoExist

    UserInfoScreenContent(
        uiState = uiState,
        isEmpty = isEmpty,
        onDosorPerDagChange = { doser -> viewModel.onDoserPerDagChange(doser) },
        onPrillorPerDosaChange = { prillor -> viewModel.onPrillorPerDosaChange(prillor) },
        onPrisPerDosaPerDosaChange = { prisPer -> viewModel.onPrisPerDosaPerDosaChange(prisPer) },
        onSaveClick = { viewModel.onSaveInClick(popUpScreen,!isBackStackEmpty) },
        popup = popUpScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoScreenContent(
    modifier: Modifier = Modifier,
    uiState: UserInfo,
    isEmpty: Boolean,
    onDosorPerDagChange: (String) -> Unit,
    onPrillorPerDosaChange: (String) -> Unit,
    onPrisPerDosaPerDosaChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    popup: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Vi behöver lite information från dig") },
                navigationIcon = {
                    if (isEmpty) {
                        IconButton(onClick = { popup() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close page"
                            )
                        }
                    }
                }
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NumberEditField(
                value = uiState.dosorPerDag,
                onNewValue = onDosorPerDagChange,
                label = "Doser per dag",
                placeHolder = "Dosor per dag",
                modifier = Modifier.fieldModifier()
            )
            NumberEditField(
                value = uiState.prillorPerDosa,
                onNewValue = onPrillorPerDosaChange,
                label = "Prillor per dosa",
                placeHolder = "Prillor per dosa",
                modifier = Modifier.fieldModifier()
            )

            NumberEditField(
                value = uiState.prisPerDosa,
                onNewValue = onPrisPerDosaPerDosaChange,
                label = "Pris per dosa",
                placeHolder = "Pris per dosa",
                modifier = Modifier.fieldModifier()
            )

            BasicButton(
                text = "Spara information",
                enabled = uiState.prisPerDosa.isNotEmpty() && uiState.dosorPerDag.isNotEmpty() && uiState.prillorPerDosa.isNotEmpty(),
                action = {
                    onSaveClick()
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewUserInfoScreen() {
    QuitSnusTheme {
        UserInfoScreen(
            popUpScreen = {}
        )
    }
}