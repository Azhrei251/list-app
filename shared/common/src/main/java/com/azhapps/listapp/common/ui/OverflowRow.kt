package com.azhapps.listapp.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

/*
 * Row that handles variably sized children overflowing gracefully. All children must be the same height
 */
@Composable
fun OverflowRow(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val rowHeight = measurables.first().minIntrinsicHeight(constraints.maxWidth)

        check(constraints.hasBoundedWidth) {
            "Unbounded width not supported"
        }
        val rowSizes = mutableListOf(0)
        var currentRow = 0
        var currentRowWidth = 0
        val placeables = measurables.map { measurable ->
            val minWidth = measurable.minIntrinsicWidth(rowHeight)
            val newRowWidth = currentRowWidth + minWidth
            val placeable = measurable.measure(
                constraints.copy(
                    minWidth = minWidth,
                    maxWidth = minWidth
                )
            )
            if (newRowWidth > constraints.maxWidth) {
                currentRow++
                rowSizes.add(1)
                currentRowWidth = placeable.width
            } else {
                rowSizes[currentRow] = rowSizes[currentRow] + 1
                currentRowWidth = newRowWidth
            }
            placeable
        }

        val height = (rowSizes.size * rowHeight).coerceIn(constraints.minHeight, constraints.maxHeight)
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            var lastX = 0
            var lastY = 0

            var currentIndex = 0
            var maxIndex = -1
            rowSizes.forEach {
                maxIndex += it
                for (i in currentIndex..maxIndex) {
                    val placeable = placeables[i]
                    placeable.place(
                        x = lastX,
                        y = lastY
                    )
                    lastX += placeable.width
                }
                lastX = 0
                lastY += rowHeight
                currentIndex = maxIndex + 1
            }
        }
    }
}