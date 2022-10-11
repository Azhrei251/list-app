package com.azhapps.listapp.groups

import com.azhapps.listapp.groups.model.GroupBottomSheetState
import com.azhapps.listapp.groups.navigation.ModifyGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.viewmodel.navigationHandle
import javax.inject.Inject

@HiltViewModel
class ModifyGroupViewModel @Inject constructor() : BaseGroupBottomSheetViewModel() {

    private val navigationHandle by navigationHandle<ModifyGroup>()

    override fun initialState() = GroupBottomSheetState(
        currentName = navigationHandle.key.group.name,
        currentMembers = navigationHandle.key.group.users,
    )
}