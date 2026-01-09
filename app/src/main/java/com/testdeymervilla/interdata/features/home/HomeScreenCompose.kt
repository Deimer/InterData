package com.testdeymervilla.interdata.features.home

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
import com.testdeymervilla.interdata.utils.mockUser
import com.testdeymervilla.presentation.R
import com.testdeymervilla.presentation.alerts.ErrorDetailCompose
import com.testdeymervilla.presentation.alerts.LoadingScreenCompose
import com.testdeymervilla.presentation.components.ButtonSize
import com.testdeymervilla.presentation.components.ButtonStyle
import com.testdeymervilla.presentation.components.DividerCompose
import com.testdeymervilla.presentation.components.TapButtonCompose
import com.testdeymervilla.presentation.components.TopBarCompose
import com.testdeymervilla.presentation.theme.InterDataTheme
import com.testdeymervilla.presentation.utils.capital
import com.testdeymervilla.repository.domain.UserDomain

@Composable
fun HomeScreenCompose(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    attributes: HomeScreenAttributes
) {
    val uiState by viewModel.uiState.collectAsState()

    when(val state = uiState) {
        is HomeUiState.Loading -> LoadingScreenCompose()
        is HomeUiState.Success -> {
            BodyContent(
                actions = attributes.actions,
                userDomain = state.user
            )
        }
        is HomeUiState.Error -> {
            val errorMessage = state.message
            ErrorDetailCompose(errorMessage, attributes.snackbarHostState)
        }
    }
}

@Composable
private fun BodyContent(
    actions: HomeScreenActions,
    userDomain: UserDomain
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(
            horizontal = dimensionResource(R.dimen.dimen_12)
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopBarCompose(
            title = stringResource(id = R.string.home),
            subtitle = stringResource(id = R.string.hi_user, userDomain.shortName),
            modifier = Modifier,
        )

        DividerCompose()

        Text(
            modifier = Modifier.fillMaxWidth().padding(
                top = dimensionResource(id = R.dimen.dimen_18),
                start = dimensionResource(id = R.dimen.dimen_16),
                end = dimensionResource(id = R.dimen.dimen_16)
            ),
            text = stringResource(R.string.identification, userDomain.identification),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            modifier = Modifier.fillMaxWidth().padding(
                top = dimensionResource(id = R.dimen.dimen_18),
                start = dimensionResource(id = R.dimen.dimen_16),
                end = dimensionResource(id = R.dimen.dimen_16)
            ),
            text = stringResource(R.string.username_tag, userDomain.username),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            modifier = Modifier.fillMaxWidth().padding(
                top = dimensionResource(id = R.dimen.dimen_18),
                start = dimensionResource(id = R.dimen.dimen_16),
                end = dimensionResource(id = R.dimen.dimen_16)
            ),
            text = stringResource(R.string.name, userDomain.fullName.capital()),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        TapButtonCompose(
            modifier = Modifier.padding(top = dimensionResource(R.dimen.dimen_46)),
            text = stringResource(id = R.string.see_schemas),
            buttonStyle = ButtonStyle.Primary,
            size = ButtonSize.Normal,
            onClick = { actions.onPrimaryAction.invoke() }
        )

        TapButtonCompose(
            modifier = Modifier.padding(top = dimensionResource(R.dimen.dimen_26)),
            text = stringResource(id = R.string.view_localities),
            buttonStyle = ButtonStyle.Secondary,
            size = ButtonSize.Normal,
            onClick = { actions.onSecondaryAction.invoke() }
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
private fun BodyContentPreview() {
    InterDataTheme {
        BodyContent(
            actions = HomeScreenActions(
                onPrimaryAction = {},
                onSecondaryAction = {}
            ),
            userDomain = mockUser
        )
    }
}