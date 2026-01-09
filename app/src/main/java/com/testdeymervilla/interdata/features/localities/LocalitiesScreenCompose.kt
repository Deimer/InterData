package com.testdeymervilla.interdata.features.localities

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
import com.testdeymervilla.interdata.utils.mockLocalities
import com.testdeymervilla.interdata.utils.toModels
import com.testdeymervilla.presentation.R
import com.testdeymervilla.presentation.alerts.ConnectionErrorScreenCompose
import com.testdeymervilla.presentation.alerts.ErrorDetailCompose
import com.testdeymervilla.presentation.alerts.LoadingScreenCompose
import com.testdeymervilla.presentation.components.ItemListCompose
import com.testdeymervilla.presentation.components.RefreshListCompose
import com.testdeymervilla.presentation.components.TopBarCompose
import com.testdeymervilla.presentation.theme.InterDataTheme
import com.testdeymervilla.repository.domain.LocalityDomain

@Composable
fun LocalitiesScreenCompose(
    viewModel: LocalitiesScreenViewModel = hiltViewModel(),
    attributes: LocalitiesScreenAttributes
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val localityList by viewModel.localityList.collectAsState()
    val isRefreshingLocalities by viewModel.isRefreshingLocalities.collectAsState()

    when(uiState) {
        is LocalitiesUiState.Success -> BodyCompose(
            actions = attributes.actions,
            localityList = localityList,
            isRefreshingLocalities = isRefreshingLocalities,
            onRefreshLocalities = { viewModel.getLocalities(true) }
        )
        is LocalitiesUiState.Loading -> LoadingScreenCompose()
        is LocalitiesUiState.ConnectionError -> ConnectionErrorScreenCompose()
        is LocalitiesUiState.Error -> {
            val errorMessage = (uiState as LocalitiesUiState.Error).message
            ErrorDetailCompose(errorMessage, attributes.snackbarHostState)
        }
    }
}

@Composable
private fun BodyCompose(
    actions: LocalitiesScreenActions,
    localityList: List<LocalityDomain>,
    isRefreshingLocalities: Boolean,
    onRefreshLocalities: () -> Unit,
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
            subtitle = stringResource(id = R.string.localities),
            modifier = Modifier,
        )
        RefreshListCompose(
            modifier = Modifier.fillMaxWidth(),
            isRefreshing = isRefreshingLocalities,
            onRefresh = onRefreshLocalities,
        ) {
            val items = localityList.toModels(
                startIconRes = R.drawable.ic_locality,
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
    val mockActions = LocalitiesScreenActions(
        onPrimaryAction = {},
        onSecondaryAction = {}
    )
    InterDataTheme {
        BodyCompose(
            actions = mockActions,
            localityList = mockLocalities,
            isRefreshingLocalities = false,
            onRefreshLocalities = {}
        )
    }
}