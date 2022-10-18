@file:OptIn(ExperimentalMaterialApi::class)

package com.azhapps.listapp.common.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import dev.enro.core.compose.dialog.BottomSheetDestination

@Composable
fun BottomSheetDestination.BottomSheetContent(
    content: @Composable () -> Unit,
) {
    BottomSheetContent {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}