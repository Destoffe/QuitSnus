package com.stoffe.quitsnus.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp

@Composable
fun SnusHeaderText1(
    text: String
){
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        lineHeight = TextUnit(34f, TextUnitType.Sp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun CardText(
    text: String
){
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 18.sp
    )
}

@Composable
fun CardTextTitle(
    text: String
){
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}