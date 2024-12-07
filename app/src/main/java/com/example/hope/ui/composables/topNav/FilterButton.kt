package com.example.hope.ui.composables.topNav

sealed class FilterButton(
    val text: String,
    val onClick: () -> Unit,
    var isHighlighted: Boolean
) {
    data object Official :
            FilterButton(
                "Official",
                {},
                isHighlighted = true
            )
    data object Komunitas :
        FilterButton(
            "Komunitas",
            {},
            isHighlighted = false
        )
    data object Article :
        FilterButton(
            "Article",
            {},
            isHighlighted = false
        )
}