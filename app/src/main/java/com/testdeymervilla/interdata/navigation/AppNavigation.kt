package com.testdeymervilla.interdata.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.testdeymervilla.interdata.features.home.HomeScreenActions
import com.testdeymervilla.interdata.features.home.HomeScreenAttributes
import com.testdeymervilla.interdata.features.home.HomeScreenCompose
import com.testdeymervilla.interdata.features.login.LoginScreenActions
import com.testdeymervilla.interdata.features.login.LoginScreenAttributes
import com.testdeymervilla.interdata.features.login.LoginScreenCompose
import com.testdeymervilla.interdata.features.schema.SchemaScreenActions
import com.testdeymervilla.interdata.features.schema.SchemaScreenAttributes
import com.testdeymervilla.interdata.features.schema.SchemaScreenCompose
import com.testdeymervilla.interdata.features.schemas.SchemasScreenActions
import com.testdeymervilla.interdata.features.schemas.SchemasScreenAttributes
import com.testdeymervilla.interdata.features.schemas.SchemasScreenCompose
import com.testdeymervilla.interdata.features.splash.SplashScreenActions
import com.testdeymervilla.interdata.features.splash.SplashScreenAttributes
import com.testdeymervilla.interdata.features.splash.SplashScreenCompose
import com.testdeymervilla.presentation.components.SnackBarCompose
import com.testdeymervilla.presentation.utils.PresentationConstants.AnimationConstants.TRANSITION_DURATION
import com.testdeymervilla.presentation.utils.toNegative
import com.testdeymervilla.interdata.navigation.AppScreens.SplashScreen
import com.testdeymervilla.interdata.navigation.AppScreens.LoginScreen
import com.testdeymervilla.interdata.navigation.AppScreens.HomeScreen
import com.testdeymervilla.interdata.navigation.AppScreens.SchemasScreen
import com.testdeymervilla.interdata.navigation.AppScreens.LocalitiesScreen
import com.testdeymervilla.interdata.navigation.AppScreens.SchemaDetailScreen
import com.testdeymervilla.interdata.navigation.AppScreens.LocalityDetailScreen
import com.testdeymervilla.interdata.navigation.AppScreens.RootGraph
import com.testdeymervilla.interdata.navigation.AppScreens.AuthGraph
import com.testdeymervilla.interdata.navigation.AppScreens.HomeGraph
import com.testdeymervilla.interdata.navigation.AppScreens.SchemasGraph
import com.testdeymervilla.interdata.navigation.AppScreens.LocalitiesGraph

@Composable
fun AppNavigation(
    snackbarHostState: SnackbarHostState
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackBarCompose(snackbarHostState) },
        content = { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding),
                color = MaterialTheme.colorScheme.background
            ) {
                BodyCompose(
                    navController = navController,
                    snackbarHostState = snackbarHostState,
                )
            }
        }
    )
}

@Composable
private fun BodyCompose(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    NavHost(
        navController = navController,
        startDestination = RootGraph.route,
    ) {
        navigation(
            route = RootGraph.route,
            startDestination = SplashScreen.route
        ) {
            composable(
                route = SplashScreen.route,
                enterTransition = { inFadeAnimation() },
                exitTransition = { outFadeAnimation() }
            ) {
                SplashScreenCompose(
                    attributes = SplashScreenAttributes(
                        actions = SplashScreenActions(
                            onPrimaryAction = {
                                navController.navigate(route = HomeGraph.route) {
                                    popUpTo(SplashScreen.route) { inclusive = true }
                                    launchSingleTop = true
                                }
                            },
                            onSecondaryAction = {
                                navController.navigate(route = AuthGraph.route) {
                                    popUpTo(SplashScreen.route) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        ),
                        snackbarHostState = snackbarHostState
                    )
                )
            }
        }

        navigation(
            route = AuthGraph.route,
            startDestination = LoginScreen.route
        ) {
            composable(
                route = LoginScreen.route,
                enterTransition = { inFadeAnimation() },
                exitTransition = { outFadeAnimation() }
            ) {
                LoginScreenCompose(
                    attributes = LoginScreenAttributes(
                        actions = LoginScreenActions(
                            onPrimaryAction = {
                                navController.navigate(route = HomeGraph.route) {
                                    popUpTo(LoginScreen.route) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        ),
                        snackbarHostState = snackbarHostState
                    )
                )
            }
        }

        navigation(
            route = HomeGraph.route,
            startDestination = HomeScreen.route
        ) {
            composable(
                route = HomeScreen.route,
                enterTransition = { inFadeAnimation() },
                exitTransition = { outFadeAnimation() }
            ) {
                HomeScreenCompose(
                    attributes = HomeScreenAttributes(
                        actions = HomeScreenActions(
                            onPrimaryAction = {
                                navController.navigate(route = SchemasGraph.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            onSecondaryAction = {
                                navController.navigate(route = LocalitiesGraph.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        ),
                        snackbarHostState = snackbarHostState
                    )
                )
            }

            navigation(
                route = SchemasGraph.route,
                startDestination = SchemasScreen.route
            ) {
                composable(
                    route = SchemasScreen.route,
                    enterTransition = { inFadeAnimation() },
                    exitTransition = { outFadeAnimation() }
                ) {
                    SchemasScreenCompose(
                        attributes = SchemasScreenAttributes(
                            actions = SchemasScreenActions(
                                onPrimaryAction = {
                                    navController.popBackStack()
                                },
                                onSecondaryAction = { schemaId ->
                                    navController.navigate(SchemaDetailScreen.createRoute(schemaId)) {
                                        launchSingleTop = true
                                    }
                                }
                            ),
                            snackbarHostState = snackbarHostState
                        )
                    )
                }
                composable(
                    route = SchemaDetailScreen.route,
                    arguments = listOf(navArgument(RouteArguments.SCHEMA_ID) { type = NavType.IntType }),
                    enterTransition = { inFadeAnimation() },
                    exitTransition = { outFadeAnimation() }
                ) { backStackEntry ->
                    val schemaId = backStackEntry.arguments?.getInt(RouteArguments.SCHEMA_ID) ?: 0
                    SchemaScreenCompose(
                        attributes = SchemaScreenAttributes(
                            schemaId = schemaId,
                            actions = SchemaScreenActions(
                                onPrimaryAction = {
                                    navController.popBackStack()
                                },
                            ),
                            snackbarHostState = snackbarHostState
                        )
                    )
                }
            }

            navigation(
                route = LocalitiesGraph.route,
                startDestination = LocalitiesScreen.route
            ) {
                composable(
                    route = LocalitiesScreen.route,
                    enterTransition = { inFadeAnimation() },
                    exitTransition = { outFadeAnimation() }
                ) {

                }
                composable(
                    route = LocalityDetailScreen.route,
                    arguments = listOf(navArgument(RouteArguments.LOCALITY_ID) { type = NavType.IntType }),
                    enterTransition = { inFadeAnimation() },
                    exitTransition = { outFadeAnimation() }
                ) { backStackEntry ->
                    val localityId = backStackEntry.arguments?.getInt(RouteArguments.LOCALITY_ID) ?: 0
                }
            }
        }
    }
}

private fun inFadeAnimation(): EnterTransition {
    return slideInHorizontally(initialOffsetX = { TRANSITION_DURATION.toNegative() }) + fadeIn()
}

private fun outFadeAnimation(): ExitTransition {
    return slideOutHorizontally(targetOffsetX = { TRANSITION_DURATION }) + fadeOut()
}