package com.dayorolands.auth.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dayorolands.auth.presentation.R
import com.dayorolands.core.presentation.designsystems.Poppins
import com.dayorolands.core.presentation.designsystems.RuniqueGray
import com.dayorolands.core.presentation.designsystems.RuniqueTheme
import com.dayorolands.core.presentation.designsystems.components.GradientBackground
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    onSignInClick : () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {
    RegisterScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
) {
    GradientBackground {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.create_account),
                style = MaterialTheme.typography.headlineMedium
            )
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = Poppins,
                        color = RuniqueGray
                    )
                ) {
                    append(stringResource(id = R.string.already_have_an_account) + " ")
                    pushStringAnnotation(
                        tag = "clickable_text",
                        annotation = stringResource(id = R.string.login)
                    )
                    withStyle(
                        style = SpanStyle(
                            fontFamily = Poppins,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append(stringResource(id = R.string.login))
                    }
                }
            }
            ClickableText(
                text = annotatedString,
                onClick = {offset ->
                    annotatedString.getStringAnnotations(
                        tag = "clickable_text",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let {
                        onAction(RegisterAction.OnLoginClick)
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    RuniqueTheme {
        RegisterScreen(
            state = RegisterState(),
            onAction = {}
        )
    }
}