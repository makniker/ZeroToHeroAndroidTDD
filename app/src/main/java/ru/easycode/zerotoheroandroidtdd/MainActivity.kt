package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.add.AddFragment
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding
import ru.easycode.zerotoheroandroidtdd.main.MainViewModel
import ru.easycode.zerotoheroandroidtdd.main.MyAdapter

class MainActivity : AppCompatActivity(), ProvideViewModel {

    private lateinit var binding: ActivityMainBinding
    private val adapter = MyAdapter()
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
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