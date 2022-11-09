package com.example.johndeere

import com.example.johndeere.databinding.ItemSubcategoryBinding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class adapterSubCategories (var context: Context, var data: List<Words>, private val functionX: (Words) -> Unit) : RecyclerView.Adapter<adapterSubCategories.ViewHolder>() {

    class ViewHolder (val binding: ItemSubcategoryBinding, functionZ: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                functionZ(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemSubcategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view) {
            functionX(data[it])
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            textView2.text = data[position].name
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}