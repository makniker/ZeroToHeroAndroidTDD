package ru.easycode.zerotoheroandroidtdd.list

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.MyApp
import ru.easycode.zerotoheroandroidtdd.core.BundleWrapper
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val adapter = MyAdapter()
    private var currentList = listOf<CharSequence>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            recyclerView.adapter = adapter
            viewModel =
                (activity?.application as MyApp).viewModelFactory.viewModel(ListViewModel::class.java)
            addButton.setOnClickListener {
                viewModel.create()
            }
            viewModel.liveData().observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.save(BundleWrapper.Mutable.Base(outState))
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            currentList = BundleWrapper.Mutable.Base(it).restore()
            viewModel.restore(BundleWrapper.Mutable.Base(it))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}