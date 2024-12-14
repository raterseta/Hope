package com.example.hope.ui.composables.topNav

sealed class FilterButton(
    val text: String,
    val onClick: () -> Unit,
    var isHighlighted: Boolean
) {
}