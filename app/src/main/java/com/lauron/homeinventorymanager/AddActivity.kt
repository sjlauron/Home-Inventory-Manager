package com.lauron.homeinventorymanager

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.lauron.homeinventorymanager.databinding.ActivityAddBinding
import com.lauron.homeinventorymanager.model.Item

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    companion object {
        const val PREFS_NAME = "MyPrefs"
        const val ITEM_LIST_KEY = "itemList"

        fun getItemsFromPreferences(preferences: SharedPreferences): List<Item> {
            val itemListString = preferences.getString(ITEM_LIST_KEY, null)
            return if (itemListString.isNullOrEmpty()) {
                emptyList()
            } else {
                // Use a proper JSON serialization library (e.g., Gson) for better handling
                val itemsArray = itemListString.split(";").toTypedArray()
                itemsArray.map {
                    val parts = it.split(",")
                    Item(parts[0])
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addDetailsBtn.setOnClickListener {
            val itemValue = binding.addItemValue.text.toString()
            val itemBuyDate = binding.addItemBuyDate.text.toString()
            val itemCategory = binding.addItemCategory.text.toString()
            val itemName = binding.addItemName.text.toString()

            saveItem(Item(itemName))

            Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveItem(item: Item) {
        val preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val itemList = getItemsFromPreferences(preferences).toMutableList()
        itemList.add(item)

        // Use a proper JSON serialization library (e.g., Gson) for better handling
        val itemListString = itemList.joinToString(";") {
            it.name
        }

        preferences.edit().putString(ITEM_LIST_KEY, itemListString).apply()
    }
}