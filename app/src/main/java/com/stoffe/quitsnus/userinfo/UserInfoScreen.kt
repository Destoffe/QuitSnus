package com.stoffe.quitsnus.userinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.stoffe.quitsnus.common.fieldModifier
import com.stoffe.quitsnus.data.UserInfo
import com.stoffe.quitsnus.ui.composable.BasicButton
import com.stoffe.quitsnus.ui.composable.BasicToolBar
import com.stoffe.quitsnus.ui.composable.NumberEditField
import com.stoffe.quitsnus.ui.theme.QuitSnusTheme

@Composable
fun UserInfoScreen(
    viewModel: UserInfoViewModel = hiltViewModel<UserInfoViewModel>(),
    popUpScreen: () -> Unit,
){
    val uiState by viewModel.userInfo

    UserInfoScreenContent(
        uiState = uiState,
        onDosorPerDagChange = { doser -> viewModel.onDoserPerDagChange(doser) },
        onPrillorPerDosaChange = { prillor -> viewModel.onPrillorPerDosaChange(prillor) },
        onPrisPerDosaPerDosaChange = { prisPer -> viewModel.onPrisPerDosaPerDosaChange(prisPer) },
        onSaveClick =  {viewModel.onSaveInClick(popUpScreen)}
    )
}

@Composable
fun UserInfoScreenContent(
    modifier: Modifier = Modifier,
    uiState: UserInfo,
    onDosorPerDagChange: (String) -> Unit,
    onPrillorPerDosaChange: (String) -> Unit,
    onPrisPerDosaPerDosaChange: (String) -> Unit,
    onSaveClick: () -> Unit,
){
    BasicToolBar(title = "Vi behöver lite information från dig")
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NumberEditField(
            value = uiState.dosorPerDag.toString(),
            onNewValue = onDosorPerDagChange,
            label = "Doser per dag",
            placeHolder = "Dosor per dag",
            modifier = Modifier.fieldModifier()
        )
        NumberEditField(
            value = uiState.prillorPerDosa.toString(),
            onNewValue = onPrillorPerDosaChange,
            label = "Prillor per dosa",
            placeHolder = "Prillor per dosa",
            modifier = Modifier.fieldModifier()
        )

        NumberEditField(
            value = uiState.prisPerDosa.toString(),
            onNewValue = onPrisPerDosaPerDosaChange,
            label = "Pris per dosa",
            placeHolder = "Pris per dosa",
            modifier = Modifier.fieldModifier()
        )

        BasicButton(text = "Spara information") {
            onSaveClick()
        }
    }
}

@Preview
@Composable
fun PreviewUserInfoScreen(){
    QuitSnusTheme {
        UserInfoScreen {

        }
    }
}