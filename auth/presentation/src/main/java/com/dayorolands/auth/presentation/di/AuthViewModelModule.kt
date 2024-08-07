package com.dayorolands.auth.presentation.di

import com.dayorolands.auth.presentation.login.LoginViewModel
import com.dayorolands.auth.presentation.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val authViewModelModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
}