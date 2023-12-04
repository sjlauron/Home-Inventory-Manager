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
    }
}