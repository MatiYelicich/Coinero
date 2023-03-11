package com.matiyelicich.coinero.common.composable

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.Configuration
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matiyelicich.coinero.common.ext.putO
import java.util.*
import com.matiyelicich.coinero.R

@Composable
fun DatePickerField(date: MutableState<String>, context: Context) {

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val theme = when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_NO -> R.style.DatePickerDialogThemeLight
        Configuration.UI_MODE_NIGHT_YES -> R.style.DatePickerDialogThemeDark
        else -> R.style.DatePickerDialogThemeLight
    }

    val datePickerDialog = DatePickerDialog(
        context,
        theme,
        { _: DatePicker, year1: Int, month1: Int, dayOfMonth1: Int ->
            date.value = "${putO(dayOfMonth1)}/${putO(month1 + 1)}/${year1}"
        }, year, month, day
    )

    DateField(
        value = date.value,
        onClick = { datePickerDialog.show() }
    )
}

@Composable
fun DatePickerText(date: MutableState<String>, context: Context) {

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val theme = when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_NO -> R.style.DatePickerDialogThemeLight
        Configuration.UI_MODE_NIGHT_YES -> R.style.DatePickerDialogThemeDark
        else -> R.style.DatePickerDialogThemeLight
    }

    val datePickerDialog = DatePickerDialog(
        context,
        theme,
        { _: DatePicker, year1: Int, month1: Int, dayOfMonth1: Int ->
            date.value = "${putO(dayOfMonth1)}/${putO(month1 + 1)}/${year1}"
        }, year, month, day
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(onClick = { day -1 }) {
            Icon(
                imageVector = Icons.Rounded.ChevronLeft,
                contentDescription = "Previous day"
            )
        }
        Text(
            text = date.value,
            modifier = Modifier.clickable { datePickerDialog.show() },
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        IconButton(onClick = { day +1 }) {
            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = "Next day"
            )
        }
    }
}

fun actualDate(): String {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    return "${putO(day)}/${putO(month + 1)}/${year}"
}