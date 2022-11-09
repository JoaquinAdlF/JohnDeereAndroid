package com.example.johndeere

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.adapters.SearchViewBindingAdapter.OnQueryTextChange
import androidx.databinding.adapters.SearchViewBindingAdapter.OnQueryTextSubmit
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.johndeere.databinding.FragmentSearchBinding
import com.example.johndeere.databinding.FragmentWordsBinding
import java.util.*

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

        for (category in categoriesList) {
            for (word in category.subCategory) {
                dataList.add(word)
            }
        }




        //searchRVAdapter.notifyDataSetChanged()

        val searchView = binding.searchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return false
            }
        })
    }

    private fun filter(text: String) {
        val filteredlist: ArrayList<Words> = ArrayList()

        for (item in dataList) {
            if (item.name.toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(context, "No hay coincidencias", Toast.LENGTH_SHORT).show()
        } else {
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


}