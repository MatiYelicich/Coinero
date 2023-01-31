package com.matiyelicich.coinero.common.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.matiyelicich.coinero.common.ext.onlyTwoPoints
import com.matiyelicich.coinero.R

@Composable
fun TitleField(
    value: String,
    onValueChange: (String) -> Unit,
    focus: FocusManager,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = { onValueChange(it.take(40)) },
        label = { Text(text = stringResource(R.string.title)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Title,
                contentDescription = "Title text field"
            )
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focus.moveFocus(FocusDirection.Down)
            }
        ),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
fun AmountField(
    value: String,
    onValueChange: (String) -> Unit,
    focus: FocusManager,
    isError: Boolean,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = { onValueChange((onlyTwoPoints(it)).take(7)) },
        label = { Text(text = stringResource(R.string.amount)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.AttachMoney,
                contentDescription = "Amount text field"
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focus.clearFocus()
            }
        ),
        maxLines = 1,
        singleLine = true,
        isError = isError
    )
}

@Composable
fun ModeField(
    value: String,
    onClick: () -> Unit,
) {
    val textColor = MaterialTheme.colors.onBackground
    OutlinedTextField(
        value = if (value == "None") stringResource(R.string.choose_category) else value,
        onValueChange = {},
        label = { Text(text = stringResource(R.string.category)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Category,
                contentDescription = "Category selector"
            )
        },
        singleLine = true,
        readOnly = true,
        maxLines = 1,
        enabled = false,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledTextColor = if (value == "None") textColor.copy(ContentAlpha.disabled) else textColor
        )
    )
}

@Composable
fun DateField(
    value: String,
    onClick: () -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = { Text(text = stringResource(R.string.date)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.CalendarToday,
                contentDescription = "Date selector"
            )
        },
        singleLine = true,
        readOnly = true,
        maxLines = 1,
        enabled = false,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledTextColor = MaterialTheme.colors.onBackground
        )
    )
}