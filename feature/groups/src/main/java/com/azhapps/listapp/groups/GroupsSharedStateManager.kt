package com.azhapps.listapp.groups

import com.azhapps.listapp.common.model.Group
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object GroupsSharedStateManager {
    private val _flow by lazy {
        MutableSharedFlow<Event>()
    }
    val flow = _flow as SharedFlow<Event>

    suspend fun dispatch(event: Event) {
        _flow.emit(event)
    }

    sealed interface Event {
        data class GroupUpdate(
            val group: Group,
        ) : Event

        data class GroupDelete(
            val groupId: Int,
        ) : Event
    }
}
