package ru.easycode.zerotoheroandroidtdd.folder.list

import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class FolderAdapter(private val listener: OnClickListener): ListAdapter<FolderUi, FolderAdapter.FolderUiViewHolder>(DIFF_UTIL) {
    object DIFF_UTIL {

    }

    inner class FolderUiViewHolder(view: View): ViewHolder(view) {
        fun bind(item: FolderUi) {
            itemView.setOnClickListener {
                listener
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderUiViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: FolderUiViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}