package com.testdeymervilla.interdata.navigation

import com.testdeymervilla.interdata.navigation.Graphs.AUTH_GRAPH
import com.testdeymervilla.interdata.navigation.Graphs.LOCALITIES_GRAPH
import com.testdeymervilla.interdata.navigation.Graphs.HOME_GRAPH
import com.testdeymervilla.interdata.navigation.Graphs.ROOT_GRAPH
import com.testdeymervilla.interdata.navigation.Graphs.SCHEMAS_GRAPH
import com.testdeymervilla.interdata.navigation.Routes.HOME
import com.testdeymervilla.interdata.navigation.Routes.LOCALITIES
import com.testdeymervilla.interdata.navigation.Routes.LOCALITY
import com.testdeymervilla.interdata.navigation.Routes.LOGIN
import com.testdeymervilla.interdata.navigation.Routes.SCHEMA
import com.testdeymervilla.interdata.navigation.Routes.SCHEMAS
import com.testdeymervilla.interdata.navigation.Routes.SPLASH

object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val HOME = "home"
    const val SCHEMAS = "schemas"
    const val SCHEMA = "schema"
    const val LOCALITIES = "localities"
    const val LOCALITY = "locality"
}

object Graphs {
    const val ROOT_GRAPH = "root_graph"
    const val AUTH_GRAPH = "auth_graph"
    const val HOME_GRAPH = "home_graph"
    const val SCHEMAS_GRAPH = "schemas_graph"
    const val LOCALITIES_GRAPH = "localities_graph"
}

object RouteArguments {
    const val SCHEMA_ID = "schemaId"
    const val LOCALITY_ID = "localityId"
}

sealed class AppScreens(val route: String) {
    object SplashScreen: AppScreens(SPLASH)
    object LoginScreen: AppScreens(LOGIN)
    object HomeScreen: AppScreens(HOME)
    object SchemasScreen: AppScreens(SCHEMAS)
    object SchemaDetailScreen: AppScreens("$SCHEMA/{${RouteArguments.SCHEMA_ID}}") {
        fun createRoute(schemaId: Int) = "$SCHEMA/$schemaId"
    }
    object LocalitiesScreen: AppScreens(LOCALITIES)
    object LocalityDetailScreen: AppScreens("$LOCALITY/{${RouteArguments.LOCALITY_ID}}") {
        fun createRoute(localityId: String) = "$LOCALITY/$localityId"
    }

    object RootGraph: AppScreens(ROOT_GRAPH)
    object AuthGraph: AppScreens(AUTH_GRAPH)
    object HomeGraph: AppScreens(HOME_GRAPH)
    object SchemasGraph: AppScreens(SCHEMAS_GRAPH)
    object LocalitiesGraph: AppScreens(LOCALITIES_GRAPH)
}