package com.azhapps.listapp.common

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import dev.enro.core.NavigationInstruction
import dev.enro.core.NavigationKey
import dev.enro.core.compose.EmptyBehavior
import dev.enro.core.compose.EnroContainer
import dev.enro.core.compose.rememberEnroContainerController

abstract class BaseActivity : FragmentActivity() {

    abstract val initialState: List<NavigationInstruction.Open>
    open val navigationKeyFilter: (NavigationKey) -> Boolean = { true }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ListAppTheme {
                val containerController = rememberEnroContainerController(
                    initialState = initialState,
                    emptyBehavior = EmptyBehavior.CloseParent,
                    accept = navigationKeyFilter,
                )
                Column(Modifier.fillMaxSize()) {
                    EnroContainer(
                        controller = containerController
                    )
                }
            }
        }
    }
}
