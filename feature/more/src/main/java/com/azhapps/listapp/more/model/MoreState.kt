package com.azhapps.listapp.more.model

data class MoreState(
    val items: List<MoreLineState> = emptyList(),
    val showLogoutConfirmDialog: Boolean = false,
)