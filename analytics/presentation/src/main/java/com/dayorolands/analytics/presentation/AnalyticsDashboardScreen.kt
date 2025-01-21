package com.dayorolands.analytics.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dayorolands.analytics.presentation.components.AnalyticsCard
import com.dayorolands.core.presentation.designsystems.RuniqueTheme
import com.dayorolands.core.presentation.designsystems.components.RuniqueScaffold
import com.dayorolands.core.presentation.designsystems.components.RuniqueToolbar
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnalyticsDashboardScreen(
    modifier: Modifier = Modifier,
    state: AnalyticsDashboardState?,
    onAction: (AnalyticsAction) -> Unit
) {
    RuniqueScaffold(
        topAppBar = {
            RuniqueToolbar(
                showBackButton = true,
                title = stringResource(id = R.string.analytics),
                onBackClick = {
                    onAction(AnalyticsAction.OnBackClick)
                }
            )
        }
    ) { padding ->
        if(state == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    AnalyticsCard(
                        title = stringResource(id = R.string.total_distance_run),
                        value = state.totalDistanceRun,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    AnalyticsCard(
                        title = stringResource(id = R.string.total_time_run),
                        value = state.totalTimeRun,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    AnalyticsCard(
                        title = stringResource(id = R.string.fastest_ever_run),
                        value = state.fastestEverRun,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    AnalyticsCard(
                        title = stringResource(id = R.string.average_distance_per_run),
                        value = state.avgDistance,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    AnalyticsCard(
                        title = stringResource(id = R.string.average_pace_per_run),
                        value = state.avgPace,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

    }
}

@Preview
@Composable
private fun AnalyticsDashboardPreview(modifier: Modifier = Modifier) {
    RuniqueTheme {
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
}