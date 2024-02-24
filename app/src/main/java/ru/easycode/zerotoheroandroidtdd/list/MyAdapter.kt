package ru.easycode.zerotoheroandroidtdd.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.databinding.ElementItemBinding

class MyAdapter : ListAdapter<CharSequence, MyAdapter.TextViewHolder>(DIFF_UTIL) {
    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<CharSequence>() {
            override fun areItemsTheSame(oldItem: CharSequence, newItem: CharSequence): Boolean =
                oldItem == newItem

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: CharSequence, newItem: CharSequence): Boolean =
                oldItem.equals(newItem)
        }
    }

    inner class TextViewHolder(private val binding: ElementItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CharSequence) {
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