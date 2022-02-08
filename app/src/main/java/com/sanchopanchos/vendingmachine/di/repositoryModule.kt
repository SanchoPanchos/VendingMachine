package com.sanchopanchos.vendingmachine.di

import com.sanchopanchos.vendingmachine.data.repository.ProductsRepo
import com.sanchopanchos.vendingmachine.data.repository.ProductsRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<ProductsRepo> {
        ProductsRepository()
    }

}