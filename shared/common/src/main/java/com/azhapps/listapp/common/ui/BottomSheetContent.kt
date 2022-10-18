@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)

package com.azhapps.listapp.common.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import dev.enro.core.compose.dialog.BottomSheetDestination
import dev.enro.core.compose.dialog.configureBottomSheet

@Composable
fun BottomSheetDestination.BottomSheetContent(
    content: @Composable () -> Unit,
) {
    configureBottomSheet {}
    ListAppTheme {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}