package com.sanchopanchos.vendingmachine.di

import com.sanchopanchos.vendingmachine.presentation.vending.VendingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        VendingViewModel(get())
    }

}