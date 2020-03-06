package com.example.nepomucenotasks.di

import com.example.nepomucenotasks.data.repository.PriorityRespository
import com.example.nepomucenotasks.data.repository.TaskRepository
import com.example.nepomucenotasks.data.repository.UserRepository
import com.example.nepomucenotasks.ui.viewModel.ViewModelPriority
import com.example.nepomucenotasks.ui.viewModel.ViewModelTask
import com.example.nepomucenotasks.ui.viewModel.ViewModelUser
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModuleUser = module {
    single { UserRepository(get()) }
    viewModel { ViewModelUser(get()) }
}

val appModulePriority = module {
    single { PriorityRespository(get()) }
    viewModel { ViewModelPriority(get()) }
}

val appModuleTask = module {
    single { TaskRepository(get()) }
    viewModel { ViewModelTask(get()) }
}

val listModules = listOf(appModuleUser, appModulePriority, appModuleTask)
