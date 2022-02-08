package com.sanchopanchos.vendingmachine.presentation.vending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanchopanchos.vendingmachine.R
import com.sanchopanchos.vendingmachine.data.model.Product
import com.sanchopanchos.vendingmachine.data.repository.ProductsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VendingViewModel(private val productsRepo: ProductsRepo) : ViewModel() {

    private val _balanceLiveData: MutableLiveData<Long> = MutableLiveData()
    val balanceLiveData: LiveData<Long> = _balanceLiveData

    private val _changeLiveData: MutableLiveData<String> = MutableLiveData()
    val changeLiveData: LiveData<String> = _changeLiveData

    private val _messageLiveData: MutableLiveData<Int> = MutableLiveData()
    val messageLiveData: LiveData<Int> = _messageLiveData

    private val _productsLiveData: MutableLiveData<List<Product>> = MutableLiveData()
    val productsLiveData: LiveData<List<Product>> = _productsLiveData

    private val coinsList = listOf(1, 5, 10, 25)

    init {
        loadProducts()
    }

    private fun loadProducts() = viewModelScope.launch(Dispatchers.IO) {
        val products = productsRepo.loadProducts()
        _productsLiveData.postValue(products)
    }

    fun purchase(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        if (currentBalance < product.price) {
            _messageLiveData.postValue(R.string.message_not_enough_money)
            return@launch
        }

        val changeMap = calculateChangeMap(currentBalance - product.price)
        _changeLiveData.postValue(formatChange(changeMap))
        _balanceLiveData.postValue(0)
        _messageLiveData.postValue(R.string.message_take_change)

        delay(5000)
        _changeLiveData.postValue("0")
    }

    private fun calculateChangeMap(change: Long): Map<Int, Int> {
        return hashMapOf<Int, Int>().also { changeMap ->
            calculateChange(change).forEach { coin ->
                if (changeMap.containsKey(coin)) {
                    changeMap[coin] = changeMap[coin]!! + coin
                } else {
                    changeMap[coin] = 1
                }
            }
        }
    }

    private fun calculateChange(change: Long): List<Int> {
        if (change == 0L) {
            return listOf()
        }

        val biggestCoin = coinsList.reversed().first { it <= change }
        if (biggestCoin.toLong() == change) {
            return listOf(biggestCoin)
        }

        return listOf(biggestCoin) + calculateChange(change - biggestCoin)
    }

    private fun formatChange(changeMap: Map<Int, Int>): String {
        val builder = StringBuilder()
        changeMap.entries.forEach { entry ->
            builder.append(entry.key).append("x").append(entry.value)
            builder.append(", ")
        }
        return builder.toString()
    }

    fun insert(amount: Long) {
        _balanceLiveData.postValue(currentBalance + amount)
    }

    private val currentBalance: Long
        get() = _balanceLiveData.value ?: 0L
}