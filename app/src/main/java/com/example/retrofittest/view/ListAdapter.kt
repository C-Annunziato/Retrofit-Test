package com.example.retrofittest.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofittest.R
import com.example.retrofittest.model.Item
import com.example.retrofittest.model.TYPE_ITEM

class ListAdapter(val items: ArrayList<Item>) :
    RecyclerView.Adapter<ListAdapter.AdapterViewHolder>() {

    fun updateItems(newItems: List<Item>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        return if (viewType == TYPE_ITEM)
            ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
            )
        else
            CategoryViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.category_layout, parent, false)
            )
    }


    abstract class AdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: Item)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class CategoryViewHolder(view: View) : ListAdapter.AdapterViewHolder(view) {
        private val categoryTitle: TextView = view.findViewById(R.id.category_title)
        override fun bind(item: Item) {
            categoryTitle.text = item.key.uppercase()
        }
    }

    class ItemViewHolder(view: View) : ListAdapter.AdapterViewHolder(view) {

        private val title: TextView = view.findViewById(R.id.item_title)
        private val text: TextView = view.findViewById(R.id.item_text)
        override fun bind(item: Item) {
            title.text = item.key
            text.text = item.value
        }


    }
}
