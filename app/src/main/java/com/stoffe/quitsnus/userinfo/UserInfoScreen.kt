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
import com.stoffe.quitsnus.login.LoginUiState
import com.stoffe.quitsnus.login.LoginViewModel
import com.stoffe.quitsnus.ui.composable.BasicButton
import com.stoffe.quitsnus.ui.composable.BasicToolBar
import com.stoffe.quitsnus.ui.composable.EmailField
import com.stoffe.quitsnus.ui.composable.NumberEditField
import com.stoffe.quitsnus.ui.composable.PasswordField
import com.stoffe.quitsnus.ui.theme.QuitSnusTheme

@Composable
fun UserInfoScreen(
    viewModel: UserInfoViewModel = hiltViewModel<UserInfoViewModel>(),
    popUpScreen: () -> Unit,
){

    val uiState by viewModel.uiState

    UserInfoScreenContent(
        uiState = uiState,
        onDoserPerDagChange = { doser -> viewModel.onDoserPerDagChange(doser) },
        onPrillorPerDosaChange = { prillor -> viewModel.onPrillorPerDosaChange(prillor) },
        onPrisPerDosaPerDosaChange = { prisPer -> viewModel.onPrisPerDosaPerDosaChange(prisPer) },
        onSaveClick = {viewModel.onSaveInClick { s, s2 ->  } }
    )
}

@Composable
fun UserInfoScreenContent(
    modifier: Modifier = Modifier,
    uiState: UserInfoUiState,
    onDoserPerDagChange: (String) -> Unit,
    onPrillorPerDosaChange: (String) -> Unit,
    onPrisPerDosaPerDosaChange: (String) -> Unit,
    onSaveClick: () -> Unit,
){
    BasicToolBar(title = "Setup information")
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NumberEditField(
            value = uiState.doserPerDag.toString(),
            onNewValue = onDoserPerDagChange,
            label = "Doser per dag",
            modifier = Modifier.fieldModifier()
        )
        NumberEditField(
            value = uiState.prillorPerDosa.toString(),
            onNewValue = onPrillorPerDosaChange,
            label = "Prillor per dosa",
            modifier = Modifier.fieldModifier()
        )

        NumberEditField(
            value = uiState.prisPerDosa.toString(),
            onNewValue = onPrisPerDosaPerDosaChange,
            label = "Pris per dosa",
            modifier = Modifier.fieldModifier()
        )

        BasicButton(text = "Save info") {
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