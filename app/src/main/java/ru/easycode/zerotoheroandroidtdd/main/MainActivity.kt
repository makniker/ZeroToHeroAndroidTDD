package ru.easycode.zerotoheroandroidtdd.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import ru.easycode.zerotoheroandroidtdd.MyApp
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.core.Screen
import ru.easycode.zerotoheroandroidtdd.create.CreateFragment
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding
import ru.easycode.zerotoheroandroidtdd.list.ListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var isFirstRun = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = (application as MyApp).viewModelFactory.viewModel(MainViewModel::class.java)
        viewModel.init(isFirstRun)
        viewModel.liveData().observe(this) {
            when(it) {
                (Screen.CreateScreen) -> {
                    supportFragmentManager.commit {
                        replace<CreateFragment>(R.id.fragment_container)
                        setReorderingAllowed(true)
                        addToBackStack("name1")
                    }
                }
                (Screen.ListScreen) -> {
                    supportFragmentManager.commit {
                        replace<ListFragment>(R.id.fragment_container)
                        setReorderingAllowed(true)
                        addToBackStack("name2")
                    }
                }
                (Screen.Pop) -> {
                    supportFragmentManager.popBackStack()
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("key", isFirstRun)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val tmp = savedInstanceState.getBoolean("key")
        if (tmp) {
            isFirstRun = false
        }
    }
}