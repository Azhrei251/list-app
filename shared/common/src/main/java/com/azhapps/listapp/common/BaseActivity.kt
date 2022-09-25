package com.azhapps.listapp.common

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import com.azhapps.listapp.common.ui.TopBar
import com.azhapps.listapp.common.ui.theme.ListAppTheme

abstract class BaseActivity : FragmentActivity(), BaseView {

    open val shouldShowBackArrow = true

    abstract fun getToolbarTitle(): String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ListAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBar(
                            title = getToolbarTitle(),
                            backAction = {
                                backAction()
                            },
                            showBackArrow = shouldShowBackArrow
                        )
                    }, content = {
                        Column(
                            modifier = Modifier
                                .padding(it)
                        ) {
                            BaseUiState(uiState)
                        }
                    })
            }
        }
    }
}
