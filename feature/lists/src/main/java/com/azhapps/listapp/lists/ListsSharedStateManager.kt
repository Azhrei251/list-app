package com.azhapps.listapp.lists

import com.azhapps.listapp.lists.model.InformativeList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object ListsSharedStateManager {
    private val _flow by lazy {
        MutableSharedFlow<Event>()
    }
    val flow = _flow as SharedFlow<Event>

    suspend fun dispatch(event: Event) {
        _flow.emit(event)
    }

    sealed interface Event {
        data class ListUpdate(
            val informativeList: InformativeList,
        ): Event

        data class ListDelete(
            val listId:Int,
        ): Event
    }
}
