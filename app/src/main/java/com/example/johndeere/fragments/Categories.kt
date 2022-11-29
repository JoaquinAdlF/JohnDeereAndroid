package com.example.johndeere.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.johndeere.GeneralCategoriesList
import com.example.johndeere.R
import com.example.johndeere.adapters.adapterCategories
import com.example.johndeere.databinding.FragmentCategoriesBinding
import com.example.johndeere.storage.TechnicalCategoriesList

/*
Fragmento que muestra las categorías principales de un lenguaje.
 */

class Categories : Fragment() {
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recibe como parámetro el lenguaje seleccionado (general o técnico)
        arguments?.let { it ->
            val language = it.getString("language") as String

            // Si el lenguaje es general, se llama al adaptador con el archivo GeneralCategoriesList
            if (language == "Lenguaje General") {
                val adapterCategory = adapterCategories(requireActivity(), GeneralCategoriesList) {
                    val bundle = Bundle()
                    bundle.putParcelable("category", it)
                    Navigation.findNavController(view).navigate(R.id.action_categories_frag_to_words_frag, bundle)
                }
                binding.lanTxt.text = language
                binding.rvCategories.adapter = adapterCategory
                binding.rvCategories.layoutManager = GridLayoutManager(requireActivity(), 2, RecyclerView.VERTICAL, false)
            }
            // Si el lenguaje es técnico, se llama al adaptador con el archivo TechnicalCategoriesList
            else if (language == "Lenguaje Técnico") {
                val adapterCategory = adapterCategories(requireActivity(), TechnicalCategoriesList) {
                    val bundle = Bundle()
                    bundle.putParcelable("category", it)
                    Navigation.findNavController(view).navigate(R.id.action_categories_frag_to_words_frag, bundle)
                }
                binding.lanTxt.text = language
                binding.rvCategories.adapter = adapterCategory
                binding.rvCategories.layoutManager = GridLayoutManager(requireActivity(), 2, RecyclerView.VERTICAL, false)
            }
        }
    }
}