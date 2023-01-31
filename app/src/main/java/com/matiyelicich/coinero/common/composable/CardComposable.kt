package com.matiyelicich.coinero.common.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matiyelicich.coinero.common.ext.punctuation
import com.matiyelicich.coinero.common.ext.twoPoints
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import com.matiyelicich.coinero.R
import com.matiyelicich.coinero.model.*

@Composable
fun CardFinance(financeModel: FinanceModel, onSwipe: () -> Unit) {

    val done = SwipeAction(
        onSwipe = { onSwipe() },
        icon = {
            Icon(
                modifier = Modifier.padding(16.dp),
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Delete",
                tint = Color.White
            )
        },
        background = Color.Red.copy(red = 0.7f)
    )

    SwipeableActionsBox(
        modifier = Modifier.clip(RoundedCornerShape(15)),
        swipeThreshold = 150.dp,
        endActions = listOf(done)
    ) {
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(15))
                .fillMaxWidth(),
            elevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(15))
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(55.dp)
                        .background(colorCategory(financeModel.category.toString())),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = iconCategory(financeModel.category.toString()),
                        contentDescription = "Icon"
                    )
                }

                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    //Title
                    Text(
                        text = financeModel.title,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Column(
                    modifier = Modifier.padding(end = 8.dp),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    //Amount
                    val amountReformat = twoPoints(financeModel.amount.toDouble())
                    Text(
                        text = punctuation(amountReformat.toString()),
                        textAlign = TextAlign.End,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    //Mode
                    val nameMode = stringResource(Modes.getStringResource(financeModel.mode.toString()))
                    Text(
                        text = nameMode,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun CardCategoryOptions(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val nameCategory = stringResource(Categories.getStringResource(title))
    Card(
        modifier = Modifier
            .height(32.dp)
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(100))
            .clickable { onClick() },
        backgroundColor = colorCategory(title),
        shape = RoundedCornerShape(100),
        border = BorderStroke(
            3.dp,
            if (selected) MaterialTheme.colors.onBackground else Color.Transparent
        ),
        elevation = 20.dp
    ) {
        Text(
            text = nameCategory,
            fontSize = 15.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun CardGeneralCategory(
    category: Categories,
    amount: Double,
) {

    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(15))
            .fillMaxWidth(),
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(15))
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .size(45.dp)
                    .clip(CircleShape)
                    .background(colorCategory(category.toString())),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = iconCategory(category.toString()),
                    contentDescription = "Icon"
                )
            }

            //Title
            val nameCategory = stringResource(Categories.getStringResource(category.toString()))
            val title = "${stringResource(R.string.balance)} ${stringResource(R.string.of)} ${nameCategory}:"
            Text(
                text = title,
                fontSize = 17.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            val amountReformat = twoPoints(amount)
            Text(
                text = punctuation(amountReformat.toString()),
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}

@Composable
fun CardFinanceContendCategorySelected(financeModel: FinanceModel, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Title
        Text(
            text = financeModel.title,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            //Amount
            val amountReformat = twoPoints(financeModel.amount.toDouble())
            Text(
                text = punctuation(amountReformat.toString()),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.End,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,color = color
            )
            //Mode
            val nameMode = stringResource(Modes.getStringResource(financeModel.mode.toString()))
            Text(
                text = nameMode,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}