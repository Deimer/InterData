package com.testdeymervilla.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.testdeymervilla.presentation.R
import com.testdeymervilla.presentation.theme.InterDataTheme
import com.testdeymervilla.presentation.theme.montserratFamily

enum class ButtonStyle { Primary, Secondary, Tertiary }
enum class ButtonSize { Normal, Small, Mini }

@Composable
fun TapButtonCompose(
    text: String,
    modifier: Modifier = Modifier,
    buttonStyle: ButtonStyle = ButtonStyle.Primary,
    size: ButtonSize = ButtonSize.Normal,
    onClick: () -> Unit
) {
    val customColors = InterDataTheme.colors
    val baseContainerColor = if (size == ButtonSize.Mini) {
        customColors.miniButton
    } else {
        customColors.primaryButton
    }

    val buttonColors = when (buttonStyle) {
        ButtonStyle.Primary -> ButtonDefaults.buttonColors(
            containerColor = baseContainerColor,
            contentColor = MaterialTheme.colorScheme.tertiaryContainer
        )
        ButtonStyle.Secondary -> ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = baseContainerColor
        )
        ButtonStyle.Tertiary -> ButtonDefaults.buttonColors(
            containerColor = customColors.miniButton,
            contentColor = customColors.onPrimaryButton
        )
    }
    val borderStroke = if(buttonStyle == ButtonStyle.Secondary) {
        BorderStroke(
            width = dimensionResource(R.dimen.dimen_2),
            color = baseContainerColor
        )
    } else null
    val buttonPadding = when(size) {
        ButtonSize.Normal -> PaddingValues(
            horizontal = dimensionResource(R.dimen.dimen_24),
            vertical = dimensionResource(R.dimen.dimen_12)
        )
        ButtonSize.Small -> PaddingValues(all = dimensionResource(R.dimen.dimen_8))
        ButtonSize.Mini -> PaddingValues(all = dimensionResource(R.dimen.dimen_4))
    }
    val tapButtonStyle = TextStyle(
        fontSize = when(size) {
            ButtonSize.Normal -> dimensionResource(R.dimen.font_size_18).value.sp
            ButtonSize.Small -> dimensionResource(R.dimen.font_size_14).value.sp
            ButtonSize.Mini -> dimensionResource(R.dimen.font_size_12).value.sp
        },
        fontFamily = montserratFamily
    )
    val buttonShape = when(size) {
        ButtonSize.Normal -> RoundedCornerShape(dimensionResource(R.dimen.dimen_12))
        ButtonSize.Small -> RoundedCornerShape(dimensionResource(R.dimen.dimen_8))
        ButtonSize.Mini -> RoundedCornerShape(dimensionResource(R.dimen.dimen_20))
    }
    val buttonWidth = when(size) {
        ButtonSize.Normal -> Modifier.fillMaxWidth()
        ButtonSize.Small -> Modifier.width(dimensionResource(R.dimen.dimen_140))
        ButtonSize.Mini -> Modifier.width(dimensionResource(R.dimen.dimen_90))
    }
    when(buttonStyle) {
        ButtonStyle.Primary -> {
            Button(
                onClick = onClick,
                modifier = modifier.then(buttonWidth),
                shape = buttonShape,
                colors = buttonColors,
                contentPadding = buttonPadding
            ) {
                Text(text, style = tapButtonStyle)
            }
        }
        ButtonStyle.Secondary -> {
            OutlinedButton(
                onClick = onClick,
                modifier = modifier.then(buttonWidth),
                shape = buttonShape,
                colors = buttonColors,
                contentPadding = buttonPadding,
                border = borderStroke
            ) {
                Text(text, style = tapButtonStyle.copy(textAlign = TextAlign.Center))
            }
        }
        ButtonStyle.Tertiary -> {
            Button(
                onClick = onClick,
                modifier = modifier.then(buttonWidth),
                shape = buttonShape,
                colors = buttonColors,
                contentPadding = buttonPadding
            ) {
                Text(text, style = tapButtonStyle)
            }
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
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun TapButtonComposePreview() {
    InterDataTheme {
        Column(
            modifier = Modifier
        ) {
            TapButtonCompose(
                text = "Primary Button",
                buttonStyle = ButtonStyle.Primary,
                size = ButtonSize.Normal,
                modifier = Modifier.padding(all = dimensionResource(R.dimen.dimen_12)),
                onClick = {}
            )
            TapButtonCompose(
                text = "Secondary Button",
                buttonStyle = ButtonStyle.Secondary,
                size = ButtonSize.Small,
                modifier = Modifier.padding(all = dimensionResource(R.dimen.dimen_12)),
                onClick = {}
            )
            TapButtonCompose(
                text = "Tertiary",
                buttonStyle = ButtonStyle.Tertiary,
                size = ButtonSize.Mini,
                modifier = Modifier.padding(all = dimensionResource(R.dimen.dimen_12)),
                onClick = {}
            )
        }
    }
}