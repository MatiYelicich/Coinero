package com.matiyelicich.coinero.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matiyelicich.coinero.common.ext.punctuation
import com.matiyelicich.coinero.common.ext.twoPoints
import com.matiyelicich.coinero.model.Categories
import com.matiyelicich.coinero.model.colorCategory

@Composable
fun RowText(category: String, amount: Double) {

    val amountReformat = twoPoints(amount)
    val nameCategory = stringResource(Categories.getStringResource(category))

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(11.dp)
                .clip(CircleShape)
                .background(colorCategory(category))
        )
        Text(
            text = nameCategory,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        Text(text = punctuation(amountReformat.toString()))
    }
}

@Composable
fun TwoDividersText(title: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(
            text = title,
            color = MaterialTheme.colors.onBackground.copy(ContentAlpha.disabled),
            textAlign = TextAlign.Center,
            fontSize = 13.sp
        )
    }
}