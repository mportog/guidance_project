package com.mportog.guidanceprojecttest.di

import com.mportog.guidanceprojecttest.data.service.FlowService
import com.mportog.guidanceprojecttest.flow.presentation.viewmodel.DataFlowViewModel
import com.mportog.guidanceprojecttest.flow.presentation.viewmodel.StatesFlowViewModel
import com.mportog.guidanceprojecttest.repository.FlowRepository
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val flowModule = module {
    viewModel {
        DataFlowViewModel(flowRepository = get())
    }
    viewModel {
        StatesFlowViewModel()
    }
    single { OkHttpClient.Builder().build() }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    factory<FlowService> { get<Retrofit>().create(FlowService::class.java) }
    factory { FlowRepository(flowService = get()) }
}