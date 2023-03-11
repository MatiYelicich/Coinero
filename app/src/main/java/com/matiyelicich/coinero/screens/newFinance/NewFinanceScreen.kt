package com.matiyelicich.coinero.screens.newFinance

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.matiyelicich.coinero.common.composable.*
import com.matiyelicich.coinero.common.ext.twoPoints
import com.matiyelicich.coinero.model.Categories
import com.matiyelicich.coinero.model.Modes
import com.matiyelicich.coinero.R

@Composable
fun NewFinanceScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: NewFinanceViewModel = hiltViewModel()
) {
    val focus = LocalFocusManager.current
    val context = LocalContext.current

    val showDialog = remember { mutableStateOf(false) }
    var showSnackbar by remember { mutableStateOf(false) }

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    val date = remember { mutableStateOf(actualDate()) }
    val mode = remember { mutableStateOf(Modes.Disable) }
    val category = remember { mutableStateOf(Categories.None) }
    var amountError by remember { mutableStateOf(false) }

    val modeComplete = mode.value != Modes.Disable
    val categoryComplete = category.value != Categories.None

    val allComplete =
        title.isNotEmpty() && amount.isNotEmpty() && date.value.isNotEmpty() && modeComplete && categoryComplete
    val somethingComplete =
        title.isNotEmpty() or amount.isNotEmpty() or modeComplete or categoryComplete

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            focus.clearFocus()
                            if (somethingComplete) {
                                showSnackbar = true
                            } else {
                                viewModel.returnToHome(openAndPopUp)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Return to home",
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.new_expense),
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (amount.isNotEmpty()) {
                                if (amount.toDouble() < 0.01) {
                                    amountError = true
                                    Toast.makeText(
                                        context,
                                        "El precio no puede ser 0",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    amountError = false
                                    if (allComplete) {
                                        focus.clearFocus()
                                        viewModel.returnToHome(openAndPopUp)
                                        viewModel.onFinancesCreated(
                                            title,
                                            twoPoints(amount.toDouble()).toString(),
                                            date.value,
                                            mode.value,
                                            category.value
                                        )
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Complete all fileds",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Complete all fileds",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Done,
                            contentDescription = "Return to home",
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            //Title
            TitleField(
                value = title,
                onValueChange = { title = it },
                focus = focus
            )
            //Amount
            AmountField(
                value = amount,
                onValueChange = {
                    amount = if (it.contains("-")) {
                        it.replace("-", "")
                    } else {
                        it
                    }
                },
                focus = focus,
                isError = amountError
            )
            //Mode
            TabMode(mode = mode)
            //Category
            ModeField(
                value = category.value.toString(),
                onClick = { showDialog.value = true }
            )
            if (showDialog.value) {
                DialogCategory(
                    show = showDialog,
                    categorySelected = category
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            //Date
            DatePickerField(date = date, context = context)
        }

        //Snackbar
        Box(Modifier.fillMaxSize(), Alignment.BottomCenter) {
            androidx.compose.animation.AnimatedVisibility(
                visible = showSnackbar
            ) {
                AlertSnackbar(
                    onDone = {
                        showSnackbar = false
                        viewModel.returnToHome(openAndPopUp)
                    }
                )
            }
        }
    }
}