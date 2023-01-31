package com.matiyelicich.coinero.common.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matiyelicich.coinero.R
import com.matiyelicich.coinero.model.Modes

@Composable
fun TabMode(mode: MutableState<Modes>) {

    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(MaterialTheme.colors.surface)
    ) {
        CardMode(
            mode = mode,
            selected = Modes.Efectivo,
            title = stringResource(R.string.cash),
            modifier = Modifier.weight(1f)
        )
        CardMode(
            mode = mode,
            selected = Modes.Tarjeta,
            title = stringResource(R.string.card),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun CardMode(mode: MutableState<Modes>, selected: Modes, modifier: Modifier, title: String) {

    val isSelected = mode.value == selected
    val animatedColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        animationSpec = tween(durationMillis = 500)
    )

    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(
                color = animatedColor,
                shape = RoundedCornerShape(30.dp)
            )
            .clickable { mode.value = selected },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = if (isSelected) 17.sp else 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}