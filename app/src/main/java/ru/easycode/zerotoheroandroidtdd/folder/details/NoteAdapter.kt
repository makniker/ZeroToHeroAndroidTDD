package ru.easycode.zerotoheroandroidtdd.folder.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.easycode.zerotoheroandroidtdd.databinding.NoteItemBinding

class NoteAdapter(private val clickListener: (NoteUi) -> Unit) : ListAdapter<NoteUi, NoteAdapter.NoteViewHolder>(DIFF_UTIL) {

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<NoteUi>() {
            override fun areItemsTheSame(oldItem: NoteUi, newItem: NoteUi): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: NoteUi, newItem: NoteUi): Boolean = oldItem == newItem
        }
    }

    inner class NoteViewHolder(private val binding: NoteItemBinding) : ViewHolder(binding.root) {
        fun bind(item: NoteUi) {
            binding.noteTitleTextView.text = item.title
            itemView.setOnClickListener {
                clickListener(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder =
        NoteViewHolder(
            NoteItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

}