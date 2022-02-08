package com.sanchopanchos.vendingmachine.data.repository

import com.sanchopanchos.vendingmachine.data.model.Product

interface ProductsRepo {

    suspend fun loadProducts() : List<Product>
}