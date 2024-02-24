package ru.easycode.zerotoheroandroidtdd.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.MyApp
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentCreateBinding

class CreateFragment : Fragment() {

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CreateViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity?.application as MyApp).viewModelFactory.viewModel(CreateViewModel::class.java)
        with(binding) {
            inputEditText.doAfterTextChanged {
                if (it != null) {
                    createButton.isEnabled = it.length > 2
                }
            }
            createButton.setOnClickListener {
                viewModel.add(inputEditText.text.toString())
            }
        }
    }
}