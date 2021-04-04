package com.example.cool8puzzle.di

import com.example.cool8puzzle.ui.viewmodels.HomeViewModel
import com.example.cool8puzzle.ui.viewmodels.InfoViewModel
import com.example.cool8puzzle.ui.viewmodels.SolverStepsViewModel
import com.example.cool8puzzle.ui.viewmodels.SolverViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeFragmentModule = module {
    viewModel { HomeViewModel() }
}

val infoViewModel = module {
    viewModel { InfoViewModel() }
}

val solverViewModel = module {
    viewModel { SolverViewModel() }
}

val solverStepsViewModel = module {
    viewModel { SolverStepsViewModel() }
}