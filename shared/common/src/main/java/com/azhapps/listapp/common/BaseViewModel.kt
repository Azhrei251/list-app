package com.azhapps.listapp.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel<S, A>: ViewModel() {
    protected val logTag = this::class.java.simpleName

    private val flow by lazy {
        MutableStateFlow(initialState())
    }

    protected var state
        get() = flow.value
        set(value) {
            flow.value = value
        }

    abstract fun dispatch(action: A)

    protected abstract fun initialState(): S

    protected fun updateState(block: S.() -> S) {
        state = block(state)
    }

    @Composable
    fun collectAsState(
        context: CoroutineContext = EmptyCoroutineContext
    ) = flow.collectAsState(context = context).value
}
