package br.com.concrete.magic.di

import br.com.concrete.magic.presentation.expansions.ExpansionsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {

    viewModel { ExpansionsViewModel(get()) }

    single { createGetExpansionsUseCase(get()) }

    single { createExpansionRepository(get()) }
}