package com.lauron.homeinventorymanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.lauron.homeinventorymanager.adapter.ItemListAdapter
import com.lauron.homeinventorymanager.databinding.ActivityItemListBinding
import com.lauron.homeinventorymanager.model.Item
import java.util.Locale

class ItemListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemListBinding
    private lateinit var itemAdapter: ItemListAdapter
    private val itemList = mutableListOf<Item>()
    private val filteredItemList = mutableListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        val searchView: SearchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (::itemAdapter.isInitialized) { // Check if itemAdapter is initialized
                    filterItemList(newText)
                }
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        itemAdapter = ItemListAdapter(this, itemList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = itemAdapter

        // Retrieve items from SharedPreferences
        val preferences = getSharedPreferences(AddActivity.PREFS_NAME, MODE_PRIVATE)
        itemList.addAll(AddActivity.getItemsFromPreferences(preferences))
        // Log the contents of the itemList
        //Log.d("ItemListActivity", "itemList: ${itemList.joinToString { it.name }}")
        itemAdapter.notifyDataSetChanged()
    }

    private fun filterItemList(query: String?) {
        filteredItemList.clear()
        if (query.isNullOrBlank()) {
            filteredItemList.addAll(itemList)
        } else {
            val lowerCaseQuery = query.lowercase(Locale.getDefault())
            for (item in itemList) {
                if (item.name.lowercase(Locale.getDefault()).contains(lowerCaseQuery)) {
                    filteredItemList.add(item)
                }
            }
        }
        itemAdapter.filterList(filteredItemList)
    }
}