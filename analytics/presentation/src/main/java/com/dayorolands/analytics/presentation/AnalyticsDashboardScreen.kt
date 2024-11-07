package com.dayorolands.analytics.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel

@Composable
fun AnalyticsDashboardScreenRoot(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: AnalyticsDashboardViewModel = koinViewModel()
) {

    AnalyticsDashboardScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action) {
                AnalyticsAction.OnBackClick -> onBackClick()
            }
        }
    )

}

@Composable
fun AnalyticsDashboardScreen(
    modifier: Modifier = Modifier,
    state: AnalyticsDashboardState?,
    onAction: (AnalyticsAction) -> Unit
) {

}

@Preview
@Composable
private fun AnalyticsDashboardPreview(modifier: Modifier = Modifier) {
    AnalyticsDashboardScreen(
        state = AnalyticsDashboardState(
            totalDistanceRun = "0.7 km",
            totalTimeRun = "0d 0h 0m",
            fastestEverRun = "239.7 km/h",
            avgDistance = "0.4 km",
            avgPace = "07:10"
        ),
        onAction = {}
    )
}