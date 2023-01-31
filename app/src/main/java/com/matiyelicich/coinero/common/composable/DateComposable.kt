package com.matiyelicich.coinero.common.composable

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.Configuration
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.matiyelicich.coinero.common.ext.putO
import java.util.*
import com.matiyelicich.coinero.R

@Composable
fun DatePicker(date: MutableState<String>, context: Context) {

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val theme = when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_NO -> R.style.DatePickerDialogThemeLight
        Configuration.UI_MODE_NIGHT_YES -> R.style.DatePickerDialogThemeDark
        else -> R.style.DatePickerDialogThemeLight
    }

    val theme1 = context.resources.getIdentifier(
        "DatePickerDialogTheme" +
                if (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) "Dark" else "Light",
        "style", context.packageName
    )

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

fun actualDate(): String {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    return "${putO(day)}/${putO(month + 1)}/${year}"
}