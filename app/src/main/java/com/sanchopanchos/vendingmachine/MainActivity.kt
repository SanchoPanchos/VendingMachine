package com.sanchopanchos.vendingmachine

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sanchopanchos.vendingmachine.data.model.Product
import com.sanchopanchos.vendingmachine.databinding.ActivityMainBinding
import com.sanchopanchos.vendingmachine.presentation.vending.ProductsAdapter
import com.sanchopanchos.vendingmachine.presentation.vending.VendingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var binding: ActivityMainBinding
    private val viewModel: VendingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initProductsList()
        initListeners()
        initViewModelListeners()
    }

    private fun initProductsList() {
        productsAdapter = ProductsAdapter() { product ->
            viewModel.purchase(product)
        }
        binding.productsRecycler.adapter = productsAdapter
        binding.productsRecycler.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    private fun initListeners() = with(binding) {
        amount1.setOnClickListener {
            viewModel.insert(1)
        }
        amount5.setOnClickListener {
            viewModel.insert(5)
        }
        amount10.setOnClickListener {
            viewModel.insert(10)
        }
        amount25.setOnClickListener {
            viewModel.insert(25)
        }
    }

    private fun initViewModelListeners() {
        viewModel.balanceLiveData.observe(this) {
            onBalanceUpdated(it)
        }
        viewModel.changeLiveData.observe(this) {
            onReceivedChange(it)
        }
        viewModel.productsLiveData.observe(this) {
            onProductsLoaded(it)
        }
        viewModel.messageLiveData.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    private fun onBalanceUpdated(balance: Long) {
        binding.total.text = getString(R.string.total_template, balance)
    }

    private fun onReceivedChange(change: String) {
        binding.change.text = getString(R.string.change_template, change)
    }

    private fun onProductsLoaded(products: List<Product>) {
        productsAdapter.set(products)
    }
}