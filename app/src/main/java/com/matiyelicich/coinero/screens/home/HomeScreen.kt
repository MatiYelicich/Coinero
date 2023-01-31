package com.matiyelicich.coinero.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.matiyelicich.coinero.common.ext.sortedDate
import com.matiyelicich.coinero.model.*
import kotlinx.coroutines.launch

@Composable
@ExperimentalMaterialApi
fun HomeScreen(
    openScreen: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()
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
    val scaffoldState = rememberScaffoldState(
        drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    )

    val balance =
        if (uiState is FinancesUiState.Success) (uiState as FinancesUiState.Success).finances.map { it.amount }
            .sumOf { it.toDouble() } else 0.0

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 0.dp,
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = "Open navigation drawer",
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .padding(start = 7.dp)
                            .size(28.dp)
                            .clip(CircleShape)
                            .padding(1.dp)
                            .clickable { scope.launch { scaffoldState.drawerState.open() } }
                    )
                },
                title = {
                    Text(
                        text = "Coinero",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    )
                },
                actions = {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add new expense",
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .padding(end = 7.dp)
                            .size(28.dp)
                            .clip(CircleShape)
                            .padding(1.dp)
                            .clickable { viewModel.onNewExpense(openScreen) }
                    )
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerBackgroundColor = Color.Transparent,
        drawerContent = {
            DrawerHome(openScreen, balance) { scope.launch { scaffoldState.drawerState.close() } }
        }
    ) {
        when (uiState) {
            is FinancesUiState.Error -> { DialogError() }
            FinancesUiState.Loading -> {
                CircularProgressIndicator()
            }
            is FinancesUiState.Success -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    FinancesList(
                        (uiState as FinancesUiState.Success).finances,
                        viewModel,
                        openScreen
                    )
                }
            }
        }
    }
}

@Composable
fun FinancesList(
    finances: List<FinanceModel>,
    viewModel: HomeViewModel,
    openScreen: (String) -> Unit,
) {
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
                Column(
                    Modifier
                        .clip(RoundedCornerShape(4))
                        .background(MaterialTheme.colors.surface)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val categoryTotals = finances.groupBy { it.category }
                        .mapValues { it.value.sumOf { it.amount.toDouble() } }

                    Box(contentAlignment = Alignment.Center) {
                        CircularIndicator(
                            mapValues = categoryTotals,
                            sumAmounts = sumAmounts
                        )
                    }

                    if (finances.isNotEmpty()) {
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                    }

                    categoryTotals.entries.sortedByDescending { it.value }.forEach { entry ->
                        RowText(category = entry.key.toString(), amount = entry.value)
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = stringResource(R.string.history),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
        }
        val dates = finances.map { it.date }.distinct()
        items(dates.sortedByDescending { sortedDate(it) }) { date ->
            TwoDividersText(title = date)

            val filteredList = finances.filter { it.date == date }
            filteredList.forEach { finance ->
                Spacer(modifier = Modifier.height(8.dp))
                CardFinance(
                    financeModel = finance,
                    onSwipe = { viewModel.onItemRemove(finance) }
                )
            }
        }
        item {
            if (finances.isEmpty()) {
                Box(
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth(),
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
            } else {
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }
}