package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var list = arrayListOf<String>()
    private val adapter = MyAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            recyclerView.adapter = adapter
            actionButton.setOnClickListener {
                list.add(inputEditText.text.toString())
                adapter.submitList(ArrayList(list))
                inputEditText.text?.clear()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("key", list)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        list = if(savedInstanceState.getStringArrayList("key") == null) {
            arrayListOf()
        } else {
            savedInstanceState.getStringArrayList("key")!!
        }
        adapter.submitList(list)
    }
}