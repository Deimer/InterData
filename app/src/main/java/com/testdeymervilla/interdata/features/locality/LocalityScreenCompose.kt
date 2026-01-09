package com.testdeymervilla.interdata.features.locality

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.testdeymervilla.interdata.utils.mockLocalities
import com.testdeymervilla.presentation.R
import com.testdeymervilla.presentation.alerts.ErrorDetailCompose
import com.testdeymervilla.presentation.alerts.LoadingScreenCompose
import com.testdeymervilla.presentation.components.DividerCompose
import com.testdeymervilla.presentation.components.TopBarCompose
import com.testdeymervilla.presentation.theme.InterDataTheme
import com.testdeymervilla.repository.domain.LocalityDomain

@Composable
fun LocalityScreenCompose(
    viewModel: LocalityScreenViewModel = hiltViewModel(),
    attributes: LocalityScreenAttributes
) {
    val uiState by viewModel.uiState.collectAsState()
    val localityDomain by viewModel.localityDomain.collectAsState()
    viewModel.getLocality(attributes.localityId)

    when(uiState) {
        is LocalityUiState.Success -> BodyCompose(
            actions = attributes.actions,
            localityDomain = localityDomain
        )
        is LocalityUiState.Loading -> LoadingScreenCompose()
        is LocalityUiState.Error -> {
            val errorMessage = (uiState as LocalityUiState.Error).message
            ErrorDetailCompose(errorMessage, attributes.snackbarHostState)
        }
    }
}

@Composable
private fun BodyCompose(
    actions: LocalityScreenActions,
    localityDomain: LocalityDomain?,
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
            subtitle = localityDomain?.cityAbbreviation.orEmpty(),
            modifier = Modifier,
        )

        DividerCompose()

        Text(
            modifier = Modifier.fillMaxWidth().padding(
                top = dimensionResource(id = R.dimen.dimen_18),
                start = dimensionResource(id = R.dimen.dimen_16),
                end = dimensionResource(id = R.dimen.dimen_16)
            ),
            text = stringResource(R.string.name, localityDomain?.name.orEmpty()),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            modifier = Modifier.fillMaxWidth().padding(
                top = dimensionResource(id = R.dimen.dimen_18),
                start = dimensionResource(id = R.dimen.dimen_16),
                end = dimensionResource(id = R.dimen.dimen_16)
            ),
            text = stringResource(R.string.city_code, localityDomain?.cityAbbreviation.orEmpty()),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            modifier = Modifier.fillMaxWidth().padding(
                top = dimensionResource(id = R.dimen.dimen_18),
                start = dimensionResource(id = R.dimen.dimen_16),
                end = dimensionResource(id = R.dimen.dimen_16)
            ),
            text = stringResource(R.string.zip_code, localityDomain?.zipCode.orEmpty()),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            modifier = Modifier.fillMaxWidth().padding(
                top = dimensionResource(id = R.dimen.dimen_18),
                start = dimensionResource(id = R.dimen.dimen_16),
                end = dimensionResource(id = R.dimen.dimen_16)
            ),
            text = stringResource(R.string.pickup_value, localityDomain?.pickupValue.orEmpty()),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
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
    val mockActions = LocalityScreenActions(
        onPrimaryAction = {}
    )
    InterDataTheme {
        BodyCompose(
            actions = mockActions,
            localityDomain = mockLocalities.first()
        )
    }
}