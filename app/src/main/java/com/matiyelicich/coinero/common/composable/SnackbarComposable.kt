package com.matiyelicich.coinero.common.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AlertSnackbar(onDone: () -> Unit) {
    Snackbar(
        action = {
            TextButton(onClick = { onDone() }) {
                Text(
                    text = "Salir",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colors.onSurface
                )
            }
        },
        content = { Text(text = "Si sales, no se guardarán los cambios") },
        backgroundColor = MaterialTheme.colors.surface.copy(alpha = 1f),
        modifier = Modifier.padding(12.dp),
        contentColor = Color.White,
        shape = RoundedCornerShape(10.dp)
    )
}