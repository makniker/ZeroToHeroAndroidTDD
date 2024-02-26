package ru.easycode.zerotoheroandroidtdd.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ListAdapter
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding
import ru.easycode.zerotoheroandroidtdd.presentation.ItemUi
import ru.easycode.zerotoheroandroidtdd.presentation.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.presentation.add.AddFragment
import ru.easycode.zerotoheroandroidtdd.presentation.details.DetailsFragment

class MainActivity : AppCompatActivity(), ProvideViewModel {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListAdapter<ItemUi, MyAdapter.TextViewHolder>
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            adapter = MyAdapter {
                val fr = DetailsFragment()
                val bundle = Bundle()
                bundle.putLong("id", it.id)
                fr.arguments = bundle
                fr.show(supportFragmentManager, "tag2")
            }
            recyclerView.adapter = adapter
            viewModel = viewModel(MainViewModel::class.java)
            viewModel.liveData().observe(this@MainActivity) {
                adapter.submitList(it)
            }
            viewModel.init()
            addButton.setOnClickListener {
                AddFragment().show(supportFragmentManager, "tag")
            }
        }
    }
    override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T  =
        (application as ProvideViewModel).viewModel(viewModelClass)
}