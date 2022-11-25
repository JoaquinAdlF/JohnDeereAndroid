package com.example.johndeere.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.johndeere.GeneralCategoriesList
import com.example.johndeere.R
import com.example.johndeere.Words
import com.example.johndeere.adapters.adapterSubCategories
import com.example.johndeere.databinding.FragmentMainPageBinding
import com.example.johndeere.storage.TechnicalCategoriesList
import com.google.android.material.bottomnavigation.BottomNavigationView


/*
Sección principal de la Aplicación, donde el usuario elige entre
lenguaje general o lenguaje técnico.
 */

class MainPage : Fragment() {

    lateinit var searchRV: RecyclerView
    lateinit var searchRVAdapter: adapterSubCategories
    lateinit var dataList: ArrayList<Words>

    private var _binding: FragmentMainPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        //binding.searchWords.onActionViewCollapsed()
        binding.generalLanguageButton.visibility = View.VISIBLE
        binding.technicalLanguageButton.visibility = View.VISIBLE
        binding.badResultTxt.visibility = View.GONE
        binding.rvSearch.visibility = View.GONE

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchRV = binding.rvSearch
        dataList = ArrayList()

        binding.searchWords.maxWidth = Integer.MAX_VALUE

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

        binding.badResultTxt.visibility = View.GONE
        binding.rvSearch.visibility = View.GONE
        binding.generalLanguageButton.visibility = View.VISIBLE
        binding.technicalLanguageButton.visibility = View.VISIBLE

        // Uso de adaptador de subcategorías (palabras) para la muestra de resultados en pantalla
        searchRVAdapter = adapterSubCategories(requireActivity(), dataList) {
            val bundle = Bundle()
            bundle.putParcelable("word", it)
            view?.let { it1 -> Navigation.findNavController(it1).navigate(com.example.johndeere.R.id.action_mainPage_frag_to_video_frag, bundle) }
        }
        searchRV.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        searchRV.adapter = searchRVAdapter
        searchRV.layoutManager = LinearLayoutManager(requireActivity())

        // Monitoreo de la entrada del usuario en el buscador
        binding.searchWords.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
                return false
            }
        })

        binding.searchWords.setOnCloseListener(object: SearchView.OnCloseListener,
            android.widget.SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                binding.generalLanguageButton.visibility = View.VISIBLE
                binding.technicalLanguageButton.visibility = View.VISIBLE
                binding.badResultTxt.visibility = View.GONE
                binding.rvSearch.visibility = View.GONE
                return false
            }
        })

        // Botón de lenguaje general
        binding.generalLanguageButton.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("language", "Lenguaje General")
            view.findNavController().navigate(com.example.johndeere.R.id.action_mainPage_frag_to_categories_frag, bundle)
        }

        // Botón de lenguaje técnico
        binding.technicalLanguageButton.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("language", "Lenguaje Técnico")
            view.findNavController().navigate(com.example.johndeere.R.id.action_mainPage_frag_to_categories_frag,bundle)
        }
    }

    // Filtrado de la base de datos de palabras de la aplicación a partir
    // de la entrada del usuario
    private fun filter(text: String) {

        binding.badResultTxt.visibility = View.VISIBLE
        binding.rvSearch.visibility = View.VISIBLE
        binding.generalLanguageButton.visibility = View.GONE
        binding.technicalLanguageButton.visibility = View.GONE

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

        searchRVAdapter.filterList(filteredlist)

    }
}