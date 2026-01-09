package com.testdeymervilla.interdata.features.schemas

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.testdeymervilla.interdata.utils.mockSchemas
import com.testdeymervilla.interdata.utils.toItem
import com.testdeymervilla.presentation.R
import com.testdeymervilla.presentation.alerts.ConnectionErrorScreenCompose
import com.testdeymervilla.presentation.alerts.ErrorDetailCompose
import com.testdeymervilla.presentation.alerts.LoadingScreenCompose
import com.testdeymervilla.presentation.components.ItemListCompose
import com.testdeymervilla.presentation.components.RefreshListCompose
import com.testdeymervilla.presentation.components.TopBarCompose
import com.testdeymervilla.presentation.theme.InterDataTheme
import com.testdeymervilla.repository.domain.SchemaDomain

@Composable
fun SchemasScreenCompose(
    viewModel: SchemasScreenViewModel = hiltViewModel(),
    attributes: SchemasScreenAttributes
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val schemaList by viewModel.schemaList.collectAsState()
    val isRefreshingSchemas by viewModel.isRefreshingSchemas.collectAsState()

    when(uiState) {
        is SchemasUiState.Success -> BodyCompose(
            actions = attributes.actions,
            schemaList = schemaList,
            isRefreshingSchemas = isRefreshingSchemas,
            onRefreshSchemas = { viewModel.getSchemas(true) }
        )
        is SchemasUiState.Loading -> LoadingScreenCompose()
        is SchemasUiState.ConnectionError -> ConnectionErrorScreenCompose()
        is SchemasUiState.Error -> {
            val errorMessage = (uiState as SchemasUiState.Error).message
            ErrorDetailCompose(errorMessage, attributes.snackbarHostState)
        }
    }
}

@Composable
private fun BodyCompose(
    actions: SchemasScreenActions,
    schemaList: List<SchemaDomain>,
    isRefreshingSchemas: Boolean,
    onRefreshSchemas: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(
            horizontal = dimensionResource(R.dimen.dimen_12)
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopBarCompose(
            navigationIcon = R.drawable.ic_back,
            onNavigationClick = actions.onPrimaryAction,
            title = stringResource(id = R.string.schemas),
            modifier = Modifier,
        )
        RefreshListCompose(
            modifier = Modifier.fillMaxWidth(),
            isRefreshing = isRefreshingSchemas,
            onRefresh = onRefreshSchemas,
        ) {
            val items = schemaList.toItem(
                startIconRes = R.drawable.ic_schema,
                onItemClick = actions.onSecondaryAction
            )
            ItemListCompose(
                items = items
            )
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Mode",
    showBackground = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun BodyComposePreview() {
    val mockActions = SchemasScreenActions(
        onPrimaryAction = {},
        onSecondaryAction = {}
    )
    InterDataTheme {
        BodyCompose(
            actions = mockActions,
            schemaList = mockSchemas,
            isRefreshingSchemas = false,
            onRefreshSchemas = {}
        )
    }
}