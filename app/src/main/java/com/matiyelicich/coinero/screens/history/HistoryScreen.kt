package com.matiyelicich.coinero.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.runtime.*
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
import com.matiyelicich.coinero.common.composable.*
import com.matiyelicich.coinero.common.ext.punctuation
import com.matiyelicich.coinero.common.ext.sortedDate
import com.matiyelicich.coinero.common.ext.twoPoints
import com.matiyelicich.coinero.model.FinanceModel
import com.matiyelicich.coinero.model.FinancesUiState
import com.matiyelicich.coinero.screens.categories.CategoriesViewModel

@Composable
fun HistoryScreen(
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
                        text = stringResource(R.string.history),
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
                    HistoryList(
                        (uiState as FinancesUiState.Success).finances,
                        viewModel,
                        openScreen,
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryList(
    finances: List<FinanceModel>,
    viewModel: CategoriesViewModel,
    openScreen: (String) -> Unit,
) {
    val dateSelected by remember { mutableStateOf("31/01/2023") }
    val dates = finances.filter { it.date == dateSelected }.map { it.date }.distinct()
    val totalSpentToday = finances.filter { it.date == dateSelected }.sumOf { it.amount.toDouble() }

    Column(
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.ChevronLeft,
                    contentDescription = "Previous day"
                )
            }
            Text(text = dateSelected)
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.ChevronRight,
                    contentDescription = "Next day"
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.surface),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                val amountReformat = twoPoints(totalSpentToday)
                Text(text = punctuation(amountReformat.toString()))
            }
        }

        if (dates.isNotEmpty()) {
            LazyColumn {
                items(dates.sortedByDescending { sortedDate(it) }) { date ->
                    val filteredList = finances.filter { it.date == date }
                    filteredList.forEach { finance ->
                        Spacer(modifier = Modifier.height(8.dp))
                        CardFinance(
                            financeModel = finance,
                            onSwipe = { viewModel.onItemRemove(finance) }
                        )
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.no_expense),
                        color = MaterialTheme.colors.onBackground.copy(ContentAlpha.disabled),
                        fontSize = 15.sp
                    )
                    RoundedButton(
                        title = stringResource(R.string.add_expense),
                        onClick = { viewModel.onNewExpense(openScreen) }
                    )
                }
            }
        }
    }
}