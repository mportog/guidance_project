package com.mportog.guidanceprojecttest.di

import com.mportog.guidanceprojecttest.flow.presentation.viewmodel.FlowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val flowModule = module {
    viewModel {
        FlowViewModel()
    }
}