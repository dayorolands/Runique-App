package com.dayorolands.analytics.presentation

sealed interface AnalyticsAction {
    data object OnBackClick: AnalyticsAction
}