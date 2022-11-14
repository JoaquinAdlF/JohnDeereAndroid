package com.example.johndeere.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.johndeere.GeneralCategoriesList
import com.example.johndeere.R
import com.example.johndeere.Words
import com.example.johndeere.adapters.adapterSubCategories
import com.example.johndeere.databinding.FragmentSearchBinding
import com.example.johndeere.storage.TechnicalCategoriesList
import java.util.*
import kotlin.collections.ArrayList

/*
Fragmento para la búsqueda de palabras dentro del almacenamiento local de la Aplicación
 */

class Search : Fragment() {

    lateinit var searchRV: RecyclerView
    lateinit var searchRVAdapter: adapterSubCategories
    lateinit var dataList: ArrayList<Words>

    private var _binding: FragmentSearchBinding ? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchRV = binding.rvSearch
        dataList = ArrayList()

        // Recolección de todas las palabras con las que cuenta la Aplicación
        for (category in GeneralCategoriesList) {
            for (word in category.subCategory) {
                dataList.add(word)
            }
        }
        for (category in TechnicalCategoriesList) {
            for (word in category.subCategory) {
                dataList.add(word)
            }
        }

        val searchView = binding.searchView

        // Monitoreo de la entrada del usuario en el buscador
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
                return false
            }
        })
    }

    // Filtrado de la base de datos de palabras de la aplicación a partir
    // de la entrada del usuario
    private fun filter(text: String) {
        var filteredlist: ArrayList<Words> = ArrayList()

        // Búsqueda de coincidencias
        for (item in dataList) {
            if (item.name.toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item)
            }
        }

        // Si no se encontraron coincidencias, notificar al usuario
        if (filteredlist.isEmpty()) {
            binding.badResultTxt.text = "No hay coincidencias para \"" + text + "\""
        }
        // Si el usuario no ingresó un valor, limpiar la pantalla
        else {
            binding.badResultTxt.text = ""
        }
        if (text == "") {
            filteredlist = ArrayList()
        }
        // Uso de adaptador de subcategorías (palabras) para la muestra de resultados en pantalla
            searchRVAdapter = adapterSubCategories(requireActivity(), filteredlist) {
                val bundle = Bundle()
                bundle.putParcelable("word", it)
                view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_search_frag_to_video_frag, bundle) }
            }

            searchRV.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
            searchRV.adapter = searchRVAdapter
            searchRV.layoutManager = LinearLayoutManager(requireActivity())
    }
}