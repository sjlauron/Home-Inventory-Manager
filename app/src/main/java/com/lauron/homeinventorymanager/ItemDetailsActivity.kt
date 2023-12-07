package com.lauron.homeinventorymanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lauron.homeinventorymanager.databinding.ActivityAddBinding
import com.lauron.homeinventorymanager.databinding.ActivityItemDetailsBinding

class ItemDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from intent
        val itemName = intent.getStringExtra("itemName")
        val itemCtgry = intent.getStringExtra("itemCtgry")
        val itemDate = intent.getStringExtra("itemDate")
        val itemValue = intent.getStringExtra("itemValue")

        // Set the item name to the TextView
        binding.nameDetails.text = itemName
        binding.ctgryDetails.text = itemCtgry
        binding.dateDetails.text = itemDate
        binding.priceDetails.text = itemValue
    }
}