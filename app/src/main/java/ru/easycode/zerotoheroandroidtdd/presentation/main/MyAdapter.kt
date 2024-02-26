package ru.easycode.zerotoheroandroidtdd.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.databinding.ElementItemBinding
import ru.easycode.zerotoheroandroidtdd.presentation.ItemUi

class MyAdapter(private val clickListener: (ItemUi) -> Unit) : ListAdapter<ItemUi, MyAdapter.TextViewHolder>(DIFF_UTIL) {
    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<ItemUi>() {

            override fun areItemsTheSame(oldItem: ItemUi, newItem: ItemUi): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: ItemUi, newItem: ItemUi): Boolean =
                oldItem.text == newItem.text
        }
    }

    inner class TextViewHolder(private val binding: ElementItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemUi) {
            binding.elementTextView.text = item.text
            binding.root.setOnClickListener {
                clickListener(item)
            }
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