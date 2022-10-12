package com.azhapps.listapp.more.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.ui.graphics.vector.ImageVector

data class MoreLineState(
    val title: Int,
    val action: MoreAction,
    val subtitle: Int = -1,
    val icon: ImageVector = Icons.Filled.QuestionMark,
)

