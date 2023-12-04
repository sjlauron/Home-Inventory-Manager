package com.lauron.homeinventorymanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.lauron.homeinventorymanager.adapter.ItemListAdapter
import com.lauron.homeinventorymanager.databinding.ActivityItemListBinding
import com.lauron.homeinventorymanager.model.Item

class ItemListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemListBinding
    private val itemList = mutableListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val itemAdapter = ItemListAdapter(this, itemList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = itemAdapter

        // Retrieve items from SharedPreferences
        val preferences = getSharedPreferences(AddActivity.PREFS_NAME, MODE_PRIVATE)
        itemList.addAll(AddActivity.getItemsFromPreferences(preferences))
        // Log the contents of the itemList
        //Log.d("ItemListActivity", "itemList: ${itemList.joinToString { it.name }}")
        itemAdapter.notifyDataSetChanged()
    }
}