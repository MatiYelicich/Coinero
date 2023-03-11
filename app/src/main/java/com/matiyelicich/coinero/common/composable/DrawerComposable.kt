package com.matiyelicich.coinero.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.History
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matiyelicich.coinero.CATEGORIES_SCREEN
import com.matiyelicich.coinero.HISTORY_SCREEN
import com.matiyelicich.coinero.R
import com.matiyelicich.coinero.SUMMARY_SCREEN
import com.matiyelicich.coinero.common.ext.punctuation
import com.matiyelicich.coinero.common.ext.twoPoints

@Composable
fun DrawerHome(
    openScreen: (String) -> Unit,
    balance: Double,
    closeDrawer: () -> Unit,
) {
    val amountReformat = twoPoints(balance)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .padding(16.dp, 5.dp, 16.dp, 21.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.balance),
                color = MaterialTheme.colors.onBackground.copy(
                    ContentAlpha.disabled
                ),
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = punctuation(amountReformat.toString()),
                fontWeight = FontWeight.SemiBold
            )
        }
        Divider()
        DrawerItem(
            title = stringResource(R.string.summary),
            icon = Icons.Rounded.Analytics,
            onItemClick = {
                openScreen(SUMMARY_SCREEN)
                closeDrawer()
            }
        )
        DrawerItem(
            title = stringResource(R.string.categories),
            icon = Icons.Rounded.Category,
            onItemClick = {
                openScreen(CATEGORIES_SCREEN)
                closeDrawer()
            }
        )
        DrawerItem(
            title = stringResource(R.string.history),
            icon = Icons.Rounded.History,
            onItemClick = {
                openScreen(HISTORY_SCREEN)
                closeDrawer()
            }
        )
    }
}

@Composable
fun DrawerItem(
    title: String,
    icon: ImageVector,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(40))
            .padding(6.dp)
            .clickable { onItemClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .height(45.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = title,
            style = TextStyle(fontSize = 18.sp),
            color = MaterialTheme.colors.onSurface
        )
    }
}
