package com.example.cool8puzzle.di

import com.example.cool8puzzle.solver.PuzzleSolver
import com.example.cool8puzzle.ui.viewmodels.HomeViewModel
import com.example.cool8puzzle.ui.viewmodels.InfoViewModel
import com.example.cool8puzzle.ui.viewmodels.SolverStepsViewModel
import com.example.cool8puzzle.ui.viewmodels.SolverViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeFragmentModule = module {
    viewModel { HomeViewModel() }
}

val infoViewModelModule = module {
    viewModel { InfoViewModel() }
}

val solverViewModelModule = module {
    viewModel { SolverViewModel() }
}

val solverStepsViewModelModule = module {
    viewModel { (puzzleData: String) -> SolverStepsViewModel(puzzleData, get()) }
}

val puzzleSolverModule = module {
    single { PuzzleSolver() }
}