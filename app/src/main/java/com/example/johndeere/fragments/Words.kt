package com.example.johndeere.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.johndeere.Categories
import com.example.johndeere.R
import com.example.johndeere.adapters.adapterSubCategories
import com.example.johndeere.databinding.FragmentWordsBinding

/*
Fragmento para la muestra de subcategorías (palabras) de una categoría.
 */

class Words : Fragment() {
    private var _binding: FragmentWordsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recepción de la categoría cuyas palabras se van a mostrar
        arguments?.let {
            val category = it.get("category") as Categories

            // Creación del adaptador para la alimentación del RecyclerView de palabras
            val adapterCategory = adapterSubCategories(requireActivity(), category.subCategory) {
                val bundle = Bundle()
                bundle.putParcelable("word", it)
                Navigation.findNavController(view).navigate(R.id.action_words_frag_to_video_frag, bundle)
            }

            binding.categoryText.text = category.name
            binding.rvWords.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
            binding.rvWords.adapter = adapterCategory
            binding.rvWords.layoutManager = LinearLayoutManager(requireActivity())
        }
    }
}