package ru.easycode.zerotoheroandroidtdd.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ProvideViewModel {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = (application as ProvideViewModel).viewModel(MainViewModel::class.java)
        viewModel.init(savedInstanceState == null)
        viewModel.liveData().observe(this) {
            it.show(supportFragmentManager, R.id.container)
        }
    }

    override fun <T : ViewModel> viewModel(clasz: Class<T>): T {
        TODO("Not yet implemented")
    }
}