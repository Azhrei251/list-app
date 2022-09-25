package com.azhapps.listapp.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel<S, A>: ViewModel() {
    private val _flow by lazy {
        MutableStateFlow(initialState())
    }

    private var state
        get() = _flow.value
        set(value) {
            _flow.value = value
        }

    abstract fun dispatch(action: A)

    protected abstract fun initialState(): S

    protected fun updateState(block: S.() -> S) {
        state = block(state)
    }

    @Composable
    fun collectAsState(
        context: CoroutineContext = EmptyCoroutineContext
    ) = _flow.collectAsState(context = context)
}
