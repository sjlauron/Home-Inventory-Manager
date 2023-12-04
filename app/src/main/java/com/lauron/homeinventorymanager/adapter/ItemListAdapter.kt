package com.lauron.homeinventorymanager.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lauron.homeinventorymanager.ItemDetailsActivity
import com.lauron.homeinventorymanager.databinding.HomeItemBinding
import com.lauron.homeinventorymanager.model.Item

class ItemListAdapter (
    private val activity: Activity,
    private val itemList: List<Item>
): RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>() {

    class ItemViewHolder (
        private val activity: Activity,
        private val binding: HomeItemBinding,
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.name.text = item.name
            binding.item.setOnClickListener {
                val intent = Intent(activity, ItemDetailsActivity::class.java)
                //intent.putExtra()
                activity.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HomeItemBinding.inflate(
            inflater,
            parent,
            false,
        )
        return ItemViewHolder(activity, binding)
    }

    override fun getItemCount()= itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
}