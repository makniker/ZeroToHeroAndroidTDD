package ru.easycode.zerotoheroandroidtdd

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.databinding.ElementItemBinding

interface BundleWrapper {
    interface Mutable : Save, Restore {
        class Base(private val bundle: Bundle) : Mutable {
            override fun save(list: ArrayList<CharSequence>) {
                bundle.putCharSequenceArrayList("key", list)
            }

            @RequiresApi(Build.VERSION_CODES.TIRAMISU)
            override fun restore(): List<CharSequence> {
                return bundle.getCharSequenceArrayList("key") ?: ArrayList()
            }
        }
    }

    interface Save {
        fun save(list: ArrayList<CharSequence>)
    }

    interface Restore {
        fun restore(): List<CharSequence>
    }
}

interface ListLiveDataWrapper {
    fun add(new: CharSequence)
    fun liveData(): LiveData<List<CharSequence>>

    fun save(bundle: BundleWrapper.Save)

    fun update(list: List<CharSequence>)

    class Base : ListLiveDataWrapper {
        private val liveData: MutableLiveData<ArrayList<CharSequence>> = SingleLiveEvent()
        override fun add(new: CharSequence) {
            val list = liveData.value?: ArrayList()
            list.add(new)
            update(list)
        }

        override fun liveData(): LiveData<List<CharSequence>> = liveData.map { it.toList() }

        override fun save(bundle: BundleWrapper.Save) {
            bundle.save(ArrayList(liveData.value!!))
        }

        override fun update(list: List<CharSequence>) {
            liveData.value = ArrayList(list)
        }

    }
}

class MainViewModel(
    val listLiveDataWrapper: ListLiveDataWrapper
) : ViewModel() {
    fun add(text: CharSequence) {
        listLiveDataWrapper.add(text)
    }

    fun save(bundle: BundleWrapper.Save) {
        listLiveDataWrapper.save(bundle)
    }

    fun restore(bundle: BundleWrapper.Restore) {
        val list = bundle.restore()
        listLiveDataWrapper.update(list)
    }
}


class MyApp : Application() {

    val viewModel = MainViewModel(ListLiveDataWrapper.Base())
}

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
