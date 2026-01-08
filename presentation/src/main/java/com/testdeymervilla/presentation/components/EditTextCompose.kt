package com.testdeymervilla.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.testdeymervilla.presentation.R
import com.testdeymervilla.presentation.theme.InterDataTheme
import com.testdeymervilla.presentation.theme.montserratFamily

@Composable
private fun textFieldColors(): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        focusedIndicatorColor = MaterialTheme.colorScheme.onTertiaryContainer,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.scrim,
        focusedTextColor = MaterialTheme.colorScheme.tertiaryContainer,
        unfocusedTextColor = MaterialTheme.colorScheme.tertiaryContainer,
        disabledTextColor = MaterialTheme.colorScheme.scrim,
        focusedTrailingIconColor = MaterialTheme.colorScheme.surfaceTint,
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.surfaceTint,
        disabledTrailingIconColor = MaterialTheme.colorScheme.scrim,
        focusedLabelColor = MaterialTheme.colorScheme.scrim,
        unfocusedLabelColor = MaterialTheme.colorScheme.scrim,
        focusedPlaceholderColor = MaterialTheme.colorScheme.onTertiaryContainer,
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onTertiaryContainer,
    )
}

@Composable
fun EditTextCompose(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    placeholder: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    imeAction: ImeAction = ImeAction.Done,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    maxLines: Int = 1
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions.copy(imeAction = imeAction),
        singleLine = singleLine,
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.primary,
            fontSize = dimensionResource(R.dimen.font_size_18).value.sp,
            fontFamily = montserratFamily
        ),
        colors = textFieldColors(),
        enabled = enabled,
        maxLines = maxLines
    )
}

@Preview(showBackground = true)
@Composable
private fun EditTextComposePreview() {
    InterDataTheme {
        Column(modifier = Modifier.padding(all = dimensionResource(R.dimen.dimen_8))) {
            EditTextCompose(
                value = "",
                onValueChange = {},
                label = "Input sample",
                placeholder = "Input sample"
            )
        }
    }
}

@Composable
fun UsernameEditTextCompose(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    placeholder: String = "",
    imeAction: ImeAction = ImeAction.Done,
) {
    EditTextCompose(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = label,
        placeholder = placeholder,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        imeAction = imeAction,
    )
}

@Preview(showBackground = true)
@Composable
private fun UsernameEditTextComposePreview() {
    InterDataTheme {
        Column(modifier = Modifier.padding(all = dimensionResource(R.dimen.dimen_8))) {
            UsernameEditTextCompose(
                value = "usernameTest",
                onValueChange = {},
                label = "Input sample",
                placeholder = "usernameTest",
            )
        }
    }
}

@Composable
fun PasswordEditTextCompose(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    placeholder: String = "",
    imeAction: ImeAction = ImeAction.Done,
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
    EditTextCompose(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = label,
        placeholder = placeholder,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if(passwordVisible) painterResource(id = R.drawable.ic_eye) else painterResource(id = R.drawable.ic_eye_slash)
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(painter = image, "")
            }
        },
        visualTransformation = visualTransformation,
        imeAction = imeAction,
    )
}

@Preview(showBackground = true)
@Composable
private fun PasswordEditTextComposePreview() {
    InterDataTheme {
        Column(modifier = Modifier.padding(all = dimensionResource(R.dimen.dimen_8))) {
            PasswordEditTextCompose(
                value = "",
                onValueChange = {},
                label = "Input sample",
                placeholder = "password",
            )
        }
    }
}

@Composable
fun AreaEditTextCompose(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    placeholder: String = "",
    imeAction: ImeAction = ImeAction.Done,
    enabled: Boolean = true,
    maxLines: Int = 3
) {
    EditTextCompose(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = label,
        placeholder = placeholder,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction,
            capitalization = KeyboardCapitalization.Sentences
        ),
        singleLine = false,
        maxLines = maxLines,
        enabled = enabled
    )
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = "id:pixel_5"
)
@Composable
private fun AreaEditTextComposePreview() {
    InterDataTheme {
        var description by remember { mutableStateOf("") }
        Column(modifier = Modifier.padding(all = dimensionResource(R.dimen.dimen_8))) {
            AreaEditTextCompose(
                value = description,
                onValueChange = { description = it },
                label = "Description",
                placeholder = "Input a description"
            )
        }
    }
}