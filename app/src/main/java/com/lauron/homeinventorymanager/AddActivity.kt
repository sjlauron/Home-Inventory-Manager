package com.lauron.homeinventorymanager

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.lauron.homeinventorymanager.databinding.ActivityAddBinding
import com.lauron.homeinventorymanager.model.Item
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private val calendar = Calendar.getInstance()

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
                    Item(parts[0],parts[1],parts[2],parts[3])
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the category Spinner
        val categoryAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.category_array,
            android.R.layout.simple_spinner_item
        )
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categorySpinner.adapter = categoryAdapter

        // Set up the date picker dialog
        val datePickerButton: Button = binding.datePickerButton
        val addItemDate: EditText = binding.addItemDate

        datePickerButton.setOnClickListener {
            showDatePickerDialog()
        }

        binding.addDetailsBtn.setOnClickListener {
            val itemValue = binding.addItemValue.text.toString()
            val itemBuyDate = addItemDate.text.toString()
            val itemCategory = binding.categorySpinner.selectedItem.toString()
            val itemName = binding.addItemName.text.toString()

            if(itemName.isNullOrEmpty()||itemCategory.isNullOrEmpty()||
                itemBuyDate.isNullOrEmpty()||itemValue.isNullOrEmpty()){
                Toast.makeText(this, "A field can't be empty!", Toast.LENGTH_SHORT).show()
            }else{
                saveItem(Item(itemName,itemCategory,itemBuyDate,itemValue))
                Toast.makeText(this, "Item added successfully!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                // Update the calendar and set the selected date in the EditText
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
                binding.addItemDate.setText(dateFormat.format(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.show()
    }

    private fun saveItem(item: Item) {
        val preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val itemList = getItemsFromPreferences(preferences).toMutableList()
        itemList.add(item)

        // Use a proper JSON serialization library (e.g., Gson) for better handling
        val itemListString = itemList.joinToString(";") {
            "${it.name},${it.category},${it.date},${it.price}"
        }

        preferences.edit().putString(ITEM_LIST_KEY, itemListString).apply()
    }
}