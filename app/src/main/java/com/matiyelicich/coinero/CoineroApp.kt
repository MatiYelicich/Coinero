package com.matiyelicich.coinero

import android.content.res.Resources
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.matiyelicich.coinero.common.snackbar.SnackbarManager
import com.matiyelicich.coinero.screens.categories.CategoriesScreen
import com.matiyelicich.coinero.screens.history.HistoryScreen
import com.matiyelicich.coinero.screens.home.HomeScreen
import com.matiyelicich.coinero.screens.newFinance.NewFinanceScreen
import com.matiyelicich.coinero.screens.splash.SplashScreen
import com.matiyelicich.coinero.screens.summary.SummaryScreen
import com.matiyelicich.coinero.theme.CoineroTheme
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CoineroApp(activity: CoineroActivity) {
    CoineroTheme {
        Surface(color = MaterialTheme.colors.background) {
            val appState = rememberAppState()
            val systemUiController = rememberSystemUiController()

            systemUiController.setSystemBarsColor(
                color = MaterialTheme.colors.primary,
                darkIcons = false,
                isNavigationBarContrastEnforced = true,
            )

            Scaffold(
                scaffoldState = appState.scaffoldState
            ) { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    makeItSoGraph(appState, activity)
                }
            }
        }
    }
}

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) =
    remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
        CoineroAppState(scaffoldState, navController, snackbarManager, resources, coroutineScope)
    }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@ExperimentalMaterialApi
fun NavGraphBuilder.makeItSoGraph(appState: CoineroAppState, activity: CoineroActivity) {

    composable(SPLASH_SCREEN) {
        SplashScreen(
            activity = activity,
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }
        )
    }
    composable(HOME_SCREEN) {
        HomeScreen(openScreen = { route -> appState.navigate(route) })
    }
    composable(NEW_FINANCE_SCREEN) {
        NewFinanceScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
    composable(CATEGORIES_SCREEN) {
        CategoriesScreen(
            popUpScreen = { appState.popUp() },
            openScreen = { route -> appState.navigate(route) }
        )
    }
    composable(HISTORY_SCREEN) {
        HistoryScreen(
            popUpScreen = { appState.popUp() },
            openScreen = { route -> appState.navigate(route) }
        )
    }
    composable(SUMMARY_SCREEN) {
        SummaryScreen(
            popUpScreen = { appState.popUp() },
            openScreen = { route -> appState.navigate(route) }
        )
    }
}
