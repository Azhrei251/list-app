package com.azhapps.listapp.more.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.azhapps.listapp.common.ui.BottomSheetContent
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import com.azhapps.listapp.common.ui.theme.Typography
import com.azhapps.listapp.more.PrivacyPolicy
import com.azhapps.listapp.more.R
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination
import dev.enro.core.close
import dev.enro.core.compose.dialog.BottomSheetDestination
import dev.enro.core.compose.navigationHandle

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalComposableDestination
@Composable
@NavigationDestination(PrivacyPolicy::class)
fun BottomSheetDestination.PrivacyPolicyBottomSheet() {
    BottomSheetContent {
        PrivacyPolicyContent()
    }
}

@Composable
fun PrivacyPolicyContent() {
    val navigationHandle = navigationHandle<PrivacyPolicy>()
    Column(
        modifier = Modifier.padding(start = ListAppTheme.defaultSpacing, end = ListAppTheme.defaultSpacing, top = ListAppTheme.defaultSpacing)
    ) {
        Text(
            style = Typography.h6,
            text = stringResource(id = R.string.about_app_dialog_privacy_title)
        )

        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .weight(1F)
                .verticalScroll(scrollState)
        ) {
            Text(text = stringResource(id = R.string.about_app_dialog_privacy_policy))
        }

        Divider()

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navigationHandle.close()
            }
        ) {
            Text(text = stringResource(id = R.string.about_app_privacy_button_ok))
        }
    }
}
