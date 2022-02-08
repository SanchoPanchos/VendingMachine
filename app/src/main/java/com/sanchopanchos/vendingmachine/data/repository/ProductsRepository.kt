package com.sanchopanchos.vendingmachine.data.repository

import com.sanchopanchos.vendingmachine.data.model.Product

class ProductsRepository : ProductsRepo {

    override suspend fun loadProducts(): List<Product> {
        return listOf(
            Product(name = "Coke", price = 25),
            Product(name = "Pepsi", price = 35),
            Product(name = "Soda", price = 45),
        )
    }

}