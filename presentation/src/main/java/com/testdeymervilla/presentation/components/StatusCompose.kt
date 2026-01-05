package com.testdeymervilla.presentation.components

import android.content.res.Configuration
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.testdeymervilla.presentation.R
import com.testdeymervilla.presentation.models.VersionStatus
import com.testdeymervilla.presentation.theme.IndianRed
import com.testdeymervilla.presentation.theme.InterDataTheme
import com.testdeymervilla.presentation.theme.JunoGreen
import com.testdeymervilla.presentation.theme.Silver

@Composable
fun StatusCompose(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    versionStatus: VersionStatus
) {
    Row(
        modifier = modifier.clickable { onClick.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatusDotCompose(status = versionStatus)
        Spacer(Modifier.width(dimensionResource(id = R.dimen.dimen_4)))
        Text(
            text = when (versionStatus) {
                VersionStatus.SAME -> stringResource(id = R.string.same)
                VersionStatus.LOWER -> stringResource(id = R.string.lower)
                VersionStatus.GREATER -> stringResource(id = R.string.greater)
            },
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Composable
fun StatusDotCompose(
    status: VersionStatus,
    size: Dp = dimensionResource(id = R.dimen.dimen_10)
) {
    val color = when (status) {
        VersionStatus.SAME -> JunoGreen
        VersionStatus.LOWER -> IndianRed
        VersionStatus.GREATER -> Silver
    }
    var appear by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (appear) 1f else 0.6f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
    )
    LaunchedEffect(Unit) { appear = true }
    Box(
        modifier = Modifier
            .scale(scale)
            .shadow(
                elevation = dimensionResource(id = R.dimen.dimen_6),
                shape = CircleShape,
                clip = false
            ).size(size)
            .clip(CircleShape)
            .background(color)
    )
}

@Preview(
    name = "Status – Same",
    showBackground = true
)
@Composable
private fun StatusComposeAlivePreview() {
    InterDataTheme {
        StatusCompose(
            versionStatus = VersionStatus.SAME,
            onClick = {}
        )
    }
}

@Preview(
    name = "Status – Lower (Light)",
    showBackground = true
)
@Composable
private fun StatusDeadLightPreview() {
    InterDataTheme {
        StatusCompose(
            versionStatus = VersionStatus.LOWER,
            onClick = {}
        )
    }
}

@Preview(
    name = "Status – Greater (Dark)",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun StatusUnknownDarkPreview() {
    InterDataTheme {
        StatusCompose(
            versionStatus = VersionStatus.GREATER,
            onClick = {}
        )
    }
}