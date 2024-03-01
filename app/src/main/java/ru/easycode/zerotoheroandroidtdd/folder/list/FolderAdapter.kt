package ru.easycode.zerotoheroandroidtdd.folder.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.easycode.zerotoheroandroidtdd.databinding.FolderItemBinding

class FolderAdapter(private val listener: (FolderUi) -> Unit): ListAdapter<FolderUi, FolderAdapter.FolderUiViewHolder>(DIFF_UTIL) {
    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<FolderUi>() {
            override fun areItemsTheSame(oldItem: FolderUi, newItem: FolderUi): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: FolderUi, newItem: FolderUi): Boolean = oldItem.id == newItem.id
        }
    }


    inner class FolderUiViewHolder(private val binding: FolderItemBinding): ViewHolder(binding.root) {
        fun bind(item: FolderUi) {
            binding.folderTitleTextView.text = item.title
            binding.folderCountTextView.text = item.notesCount.toString()
            binding.root.setOnClickListener {
                listener(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderUiViewHolder {
        return FolderUiViewHolder(
            FolderItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: FolderUiViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}