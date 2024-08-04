@file:OptIn(ExperimentalMaterial3Api::class)

package com.dayorolands.run.presentation.run_overview

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dayorolands.core.presentation.designsystems.AnalyticsIcon
import com.dayorolands.core.presentation.designsystems.LogoIcon
import com.dayorolands.core.presentation.designsystems.LogoutIcon
import com.dayorolands.core.presentation.designsystems.RunIcon
import com.dayorolands.core.presentation.designsystems.RuniqueTheme
import com.dayorolands.core.presentation.designsystems.components.RuniqueFloatingActionButton
import com.dayorolands.core.presentation.designsystems.components.RuniqueScaffold
import com.dayorolands.core.presentation.designsystems.components.RuniqueToolbar
import com.dayorolands.core.presentation.designsystems.components.util.DropDownItem
import com.dayorolands.run.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun RunOverviewScreenRoot(
    modifier: Modifier = Modifier,
    runOverviewViewModel: RunOverviewViewModel = koinViewModel()
) {
    RunOverviewScreen(
        modifier = modifier,
        onAction = runOverviewViewModel::onAction
    )
}

@Composable
fun RunOverviewScreen(
    modifier: Modifier = Modifier,
    onAction : (RunOverviewAction) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )
    RuniqueScaffold(
        topAppBar = {
            RuniqueToolbar(
                showBackButton = false,
                title = stringResource(id = R.string.runique),
                scrollBehavior = scrollBehavior,
                menuItems = listOf(
                    DropDownItem(
                        icon = AnalyticsIcon,
                        title = stringResource(id = R.string.analytics)
                    ),
                    DropDownItem(
                        icon = LogoutIcon,
                        title = stringResource(id = R.string.logout)
                    )
                ),
                onMenuItemClick = { index ->
                    when(index) {
                        0 -> onAction(RunOverviewAction.OnAnalyticsClick)
                        1 -> onAction(RunOverviewAction.OnLogoutClick)
                    }
                },
                startContent = {
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            RuniqueFloatingActionButton(
                icon = RunIcon,
                onClick = { onAction(RunOverviewAction.OnStartClick) }
            )
        }
    ) {}
}

@Preview
@Composable
fun RunOverviewScreenPreview(modifier: Modifier = Modifier) {
    RuniqueTheme {
        RunOverviewScreen(
            onAction = {}
        )
    }
}

