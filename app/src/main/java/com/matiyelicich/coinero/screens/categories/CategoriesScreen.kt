package com.matiyelicich.coinero.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
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
import com.matiyelicich.coinero.common.ext.punctuation
import com.matiyelicich.coinero.common.ext.sortedDate
import com.matiyelicich.coinero.common.ext.twoPoints
import com.matiyelicich.coinero.model.Categories
import com.matiyelicich.coinero.model.FinanceModel
import com.matiyelicich.coinero.model.FinancesUiState

@Composable
fun CategoriesScreen(
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
                        text = stringResource(R.string.categories),
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
                    CategoriesList(
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
fun CategoriesList(
    finances: List<FinanceModel>,
    viewModel: CategoriesViewModel,
    openScreen: (String) -> Unit,
) {
    val categoryTotals = finances.groupBy { it.category }.mapValues { it.value.sumOf { it.amount.toDouble() } }
    val categoriesList = finances.map { it.category }.distinct().sortedByDescending { categoryTotals[it] }

    var categorySelected by remember { mutableStateOf(categoriesList.firstOrNull() ?: Categories.None) }

    val nothingSelected = categorySelected != Categories.None
    val totalAmount = categoryTotals[categorySelected]
    val dates = finances.filter { it.category == categorySelected }.map { it.date }.distinct()

    Column {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(categoriesList) { category ->
                CardCategoryOptions(
                    title = category.toString(),
                    selected = category == categorySelected
                ) {
                    categorySelected = category
                }
            }
        }

        Column(modifier = Modifier.padding(horizontal = 12.dp)) {
            if (nothingSelected) {
                CardGeneralCategory(
                    category = categorySelected,
                    amount = totalAmount!!
                )
                LazyColumn {
                    items(dates.sortedByDescending { sortedDate(it) }) { date ->
                        TwoDividersText(title = date, modifier = Modifier.padding(vertical = 8.dp))
                        val filteredList = finances.filter { it.category == categorySelected }.filter { it.date == date }
                        val totalSpentToday = finances.filter { it.category == categorySelected }.filter { it.date == date }.sumOf { it.amount.toDouble() }
                        val maxMoreExpenseColor =
                            if (categorySelected == Categories.Comida) if (totalSpentToday <= 30) MaterialTheme.colors.onSurface else Color.Red.copy(
                                red = 0.7f
                            ) else MaterialTheme.colors.onSurface

                        Card(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .fillMaxWidth(),
                            backgroundColor = MaterialTheme.colors.surface,
                            elevation = 8.dp
                        ) {
                            Column {
                                filteredList.forEach { finance ->
                                    CardFinanceContendCategorySelected(
                                        financeModel = finance,
                                        color = if (filteredList.size == 1) maxMoreExpenseColor else MaterialTheme.colors.onSurface
                                    )
                                    if (filteredList.size > 1) {
                                        Divider()
                                    }
                                }
                                if (filteredList.size > 1) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(MaterialTheme.colors.surface)
                                            .padding(horizontal = 16.dp, vertical = 5.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = stringResource(R.string.total),
                                            color = MaterialTheme.colors.onBackground.copy(
                                                ContentAlpha.disabled
                                            ),
                                            modifier = Modifier.weight(1f)
                                        )
                                        val amountReformat = twoPoints(totalSpentToday)
                                        Text(
                                            text = punctuation(amountReformat.toString()),
                                            color = maxMoreExpenseColor
                                        )
                                    }
                                }
                            }
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
                            title =stringResource(R.string.add_expense),
                            onClick = { viewModel.onNewExpense(openScreen) }
                        )
                    }
                }
            }
        }
    }
}