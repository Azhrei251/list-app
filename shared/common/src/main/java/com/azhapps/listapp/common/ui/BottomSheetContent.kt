package com.azhapps.listapp.common.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import dev.enro.core.compose.dialog.BottomSheetConfiguration
import dev.enro.core.compose.dialog.BottomSheetDestination
import dev.enro.core.compose.dialog.configureBottomSheet

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetDestination.BottomSheetContent(
    config: BottomSheetConfiguration.Builder.() -> Unit = {},
    content: @Composable () -> Unit,
) {
    configureBottomSheet(config)
    ListAppTheme {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}