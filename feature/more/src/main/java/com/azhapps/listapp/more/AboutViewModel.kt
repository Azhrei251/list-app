package com.azhapps.listapp.more

import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.common.uc.GetOwnUserInfoUseCase
import com.azhapps.listapp.more.ui.AboutAction
import com.azhapps.listapp.more.ui.AboutState
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.forward
import dev.enro.viewmodel.navigationHandle
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val getOwnUserInfoUseCase: GetOwnUserInfoUseCase,
) : BaseViewModel<AboutState, AboutAction>() {

    private val navigationHandle by navigationHandle<About>()

    init {
        updateUserDetails()
    }

    override fun initialState() = AboutState()

    override fun dispatch(action: AboutAction) {
        when (action) {
            AboutAction.ShowPrivacyPolicy -> navigationHandle.forward(PrivacyPolicy)
        }
    }

    private fun updateUserDetails() {
        viewModelScope.launch {
            val result = getOwnUserInfoUseCase()
            if (result.success) {
                updateState {
                    copy(
                        userSectionState = userSectionState.copy(
                            email = result.data!!.email,
                            username = result.data!!.username,
                            uiState = UiState.Content
                        )
                    )
                }
            } else {
                updateState {
                    copy(
                        userSectionState = userSectionState.copy(
                            uiState = UiState.Error(result.error)
                        )
                    )
                }
            }
        }
    }
}