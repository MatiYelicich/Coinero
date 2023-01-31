package com.matiyelicich.coinero.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.matiyelicich.coinero.model.Categories
import com.matiyelicich.coinero.model.colorCategory
import com.matiyelicich.coinero.model.iconCategory

@Composable
fun DialogCategory(
    show: MutableState<Boolean>,
    categorySelected: MutableState<Categories>,
) {
    Dialog(
        onDismissRequest = { show.value = false }
    ) {
        Scaffold(
            backgroundColor = MaterialTheme.colors.background,
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colors.primary,
                    elevation = 0.dp,
                    navigationIcon = {
                        IconButton(onClick = { show.value = false }) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colors.onBackground
                            )
                        }
                    },
                    title = {
                        Text(
                            text = stringResource(com.matiyelicich.coinero.R.string.choose_category),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onBackground
                        )
                    }
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {}
                items(Categories.getOptions()) { category ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10))
                            .clickable {
                                categorySelected.value = Categories.valueOf(category)
                                show.value = false
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10))
                                .background(colorCategory(category)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = iconCategory(category),
                                contentDescription = stringResource(Categories.getStringResource(category)),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = stringResource(Categories.getStringResource(category)))
                    }
                }
            }
        }
    }
}


@Composable
fun DialogError() {
    Dialog(
        onDismissRequest = { }
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .width(280.dp)
                .background(MaterialTheme.colors.surface),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Ocurrió un error inesperado", fontWeight = FontWeight.SemiBold)
        }
    }
}