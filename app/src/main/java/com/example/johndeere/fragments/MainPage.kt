package com.example.johndeere.fragments

import android.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.johndeere.GeneralCategoriesList
import com.example.johndeere.Words
import com.example.johndeere.adapters.adapterSubCategories
import com.example.johndeere.api.ApiService
import com.example.johndeere.databinding.FragmentMainPageBinding
import com.example.johndeere.storage.TechnicalCategoriesList


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
        // Limpiar las preferences, eliminar/comentar despues de hacer pruebas
        //context?.getSharedPreferences("Dificultad básica", Context.MODE_PRIVATE)?.edit()?.clear()?.apply()
        //context?.getSharedPreferences("pref", Context.MODE_PRIVATE)?.edit()?.clear()?.apply()

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
            view.let { it1 -> Navigation.findNavController(it1).navigate(com.example.johndeere.R.id.action_mainPage_frag_to_video_frag, bundle) }
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

        getUserProgress()
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

    // Metodo para recuperar el progreso del empleado almacenado en la base de datos
    @SuppressLint("CommitPrefEdits")
    private fun getUserProgress(){
        val sharedPrefProfile = activity?.getSharedPreferences("pref",Context.MODE_PRIVATE)
        val editor = sharedPrefProfile?.edit()
        val getId = sharedPrefProfile?.getFloat("id", 0f)
        val id = getId?.toInt()

        if (sharedPrefProfile?.getString("username","") != ""){
            if (checkForInternet(this.requireActivity())) {
                if (id != null) {
                    ApiService.getProgress(id){
                        editor?.putFloat("id", it?.get(0)?.id!!.toFloat())
                        editor?.putFloat("level1", it?.get(0)?.level1!!.toFloat())
                        editor?.putFloat("level2", it?.get(0)?.level2!!.toFloat())
                        editor?.putFloat("level3", it?.get(0)?.level3!!.toFloat())
                    }
                }
            } else {
                AlertDialog.Builder(context)
                    .setTitle("No hay conexión a internet, no se pudo recuperar el progreso")
                    .setPositiveButton("Ok") { _, _ ->
                    }
                    .show()
            }
        }
    }

    // Metodo para verificar si hay conexion a internet
    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}