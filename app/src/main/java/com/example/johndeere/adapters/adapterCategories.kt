package com.example.johndeere.adapters

import com.example.johndeere.databinding.ItemCategoryBinding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.johndeere.Categories

/*
Adaptador para el RecyclerView de las categorías principales.
Recibe una lista(List) de las categorías de lenguaje con las que cuenta el proyecto.
 */

class adapterCategories (var context: Context, var data: List<Categories>, private val functionX: (Categories) -> Unit) : RecyclerView.Adapter<adapterCategories.ViewHolder>() {
    class ViewHolder (val binding: ItemCategoryBinding, functionZ: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                functionZ(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view) {
            functionX(data[it])
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            textViewName.text = data[position].name
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}