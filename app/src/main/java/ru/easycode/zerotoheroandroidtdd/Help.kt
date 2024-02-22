package ru.easycode.zerotoheroandroidtdd

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.databinding.ElementItemBinding

class MyAdapter : ListAdapter<String, MyAdapter.TextViewHolder>(DIFF_UTIL) {
    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }
    }

    inner class TextViewHolder(private val binding: ElementItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.elementTextView.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        return TextViewHolder(
            ElementItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}