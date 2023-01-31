package com.matiyelicich.coinero.common.composable

import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.matiyelicich.coinero.common.ext.punctuation
import com.matiyelicich.coinero.common.ext.twoPoints
import com.matiyelicich.coinero.model.Categories
import com.matiyelicich.coinero.model.colorCategory
import com.matiyelicich.coinero.R

@Composable
fun CircularIndicator(
    mapValues: Map<Categories, Double>,
    sumAmounts: Double
) {
    val colorDisable = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
    val strokeWidth = 60f
    val canvasSize = 250.dp

    Column(
        modifier = Modifier
            .size(canvasSize)
            .drawBehind {
                val componentSize = size / 1.25f
                backgroundIndicator(
                    componentSize = componentSize,
                    indicatorColor = colorDisable,
                    indicatorStrokeWidth = strokeWidth
                )
                foregroundIndicator(
                    mapValues = mapValues,
                    total = sumAmounts,
                    componentSize = componentSize,
                    indicatorStrokeWidth = strokeWidth
                )
            },

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        EmbeddedElements(bigText = sumAmounts)
    }
}

fun DrawScope.backgroundIndicator(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 0f,
        sweepAngle = 360f,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Square
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

fun DrawScope.foregroundIndicator(
    mapValues: Map<Categories, Double>,
    total: Double,
    componentSize: Size,
    indicatorStrokeWidth: Float
) {
    var startAngle = 0f

    for (entry in mapValues.entries) {
        val category = entry.key
        val value = entry.value.toFloat()
        val sweepAngle = value / total.toFloat() * 360

        drawArc(
            size = componentSize,
            color = colorCategory(category.toString()),
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(
                width = indicatorStrokeWidth,
                cap = StrokeCap.Butt
            ),
            topLeft = Offset(
                x = (size.width - componentSize.width) / 2f,
                y = (size.height - componentSize.height) / 2f
            )
        )
        startAngle += sweepAngle
    }
}

@Composable
fun EmbeddedElements(bigText: Double) {

    val value = twoPoints(bigText).toString()

    Text(
        text = stringResource(R.string.total),
        fontSize = 16.sp,
        textAlign = TextAlign.Center
    )
    Text(
        text = punctuation(value),
        fontSize = 18.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold
    )
}