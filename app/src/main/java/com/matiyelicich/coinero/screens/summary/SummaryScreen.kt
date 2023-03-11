package com.matiyelicich.coinero.screens.summary

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.matiyelicich.coinero.R
import com.matiyelicich.coinero.common.composable.CircularIndicator
import com.matiyelicich.coinero.common.composable.RowText
import com.matiyelicich.coinero.common.ext.punctuation
import com.matiyelicich.coinero.common.ext.twoPoints
import com.matiyelicich.coinero.model.*
import com.matiyelicich.coinero.screens.categories.CategoriesViewModel

@Composable
fun SummaryScreen(
    popUpScreen: () -> Unit,
    openScreen: (String) -> Unit,
    viewModel: CategoriesViewModel = hiltViewModel(),
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<FinancesUiState>(
        initialValue = FinancesUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 0.dp,
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Back to home screen",
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .padding(start = 7.dp)
                            .size(28.dp)
                            .clip(CircleShape)
                            .padding(1.dp)
                            .clickable { popUpScreen() }
                    )
                },
                title = {
                    Text(
                        text = stringResource(R.string.summary),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            )
        }
    ) {
        when (uiState) {
            is FinancesUiState.Error -> {}
            FinancesUiState.Loading -> {
                CircularProgressIndicator()
            }
            is FinancesUiState.Success -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    SummaryList(
                        (uiState as FinancesUiState.Success).finances,
                        //viewModel,
                        openScreen,
                    )
                }
            }
        }
    }
}

@Composable
fun SummaryList(
    finances: List<FinanceModel>,
    //viewModel: CategoriesViewModel,
    openScreen: (String) -> Unit,
) {
    val categoryTotals = finances.groupBy { it.category }
        .mapValues { it.value.sumOf { it.amount.toDouble() } }

    LazyColumn(
        modifier = Modifier.padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.summary),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
        }
        item {
            val amounts = finances.map { it.amount }
            val sumAmounts =
                amounts.sumOf { it.toDouble() } //Efectivo o Tarjeta, igual que las categorias

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4)),
                elevation = 8.dp
            ) {
                Box(
                    Modifier
                        .clip(RoundedCornerShape(4))
                        .background(MaterialTheme.colors.surface)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularIndicator(
                        mapValues = categoryTotals,
                        sumAmounts = sumAmounts
                    )
                }
            }
        }
        item {
            Text(
                text = stringResource(R.string.categories),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4)),
                elevation = 8.dp
            ) {
                Column(
                    Modifier
                        .clip(RoundedCornerShape(4))
                        .background(MaterialTheme.colors.surface)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    categoryTotals.entries.sortedByDescending { it.value }.forEach { entry ->
                        RowText(category = entry.key.toString(), amount = entry.value)
                    }
                }
            }
        }
        item {
            Text(
                text = stringResource(R.string.modes),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4)),
                elevation = 8.dp
            ) {
                Column(
                    Modifier
                        .clip(RoundedCornerShape(4))
                        .background(MaterialTheme.colors.surface)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val modesTotal = finances.groupBy { it.mode }
                        .mapValues { it.value.sumOf { it.amount.toDouble() } }

                    modesTotal.entries.sortedByDescending { it.value }.forEach { entry ->

                        val amountReformat = twoPoints(entry.value)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val nameMode = stringResource(Modes.getStringResource(entry.key.toString()))
                            Text(
                                text = nameMode,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 8.dp)
                            )
                            Text(text = punctuation(amountReformat.toString()))
                        }
                    }
                }
            }
        }
    }
}