package com.matiyelicich.coinero.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Fingerprint
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.matiyelicich.coinero.CoineroActivity
import kotlinx.coroutines.delay
import com.matiyelicich.coinero.R
import com.matiyelicich.coinero.common.composable.BasicButton

private const val SPLASH_TIMEOUT = 500L

@Composable
fun SplashScreen(
    activity: CoineroActivity,
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    var auth by remember { mutableStateOf(false) }

    Column(
        modifier =
        modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewModel.showError.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = stringResource(R.string.generic_error))
                BasicButton(
                    text = R.string.try_again,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 8.dp)
                ) {
                    viewModel.onAppStart(openAndPopUp)
                }
            }

        } else {

            Spacer(modifier = Modifier.height(50.dp))
            Icon(
                imageVector = Icons.Rounded.Lock,
                contentDescription = "App locked",
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${stringResource(R.string.app_name)} ${stringResource(R.string.locked)}",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(300.dp))
            Icon(
                imageVector = Icons.Rounded.Fingerprint,
                contentDescription = "App locked",
                tint = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.fingerprint),
                fontSize = 15.sp,
                color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
            )
        }
    }

    LaunchedEffect(true) {
        delay(SPLASH_TIMEOUT)
        CoineroActivity.authenticate(activity) { auth = it }
    }

    if (auth) viewModel.onAppStart(openAndPopUp)
}


