package com.sanchopanchos.vendingmachine.presentation.vending

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sanchopanchos.vendingmachine.R
import com.sanchopanchos.vendingmachine.data.model.Product
import com.sanchopanchos.vendingmachine.databinding.ItemProductBinding

class ProductsAdapter(val listener: ((Product) -> Unit)) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private val products = mutableListOf<Product>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size

    fun set(newProducts: List<Product>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) = with(binding) {
            name.text = product.name
            price.text = root.context.getString(R.string.price_template, product.price)
            buy.setOnClickListener {
                listener.invoke(product)
            }
        }

    }
}