package com.azhapps.listapp.groups

import com.azhapps.listapp.groups.model.GroupBottomSheetState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(): BaseGroupBottomSheetViewModel() {
    override fun initialState() = GroupBottomSheetState()
}