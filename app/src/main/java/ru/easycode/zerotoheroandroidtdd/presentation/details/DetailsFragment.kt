package ru.easycode.zerotoheroandroidtdd.presentation.details

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.presentation.ProvideViewModel

class DetailsFragment : BottomSheetDialogFragment(R.layout.fragment_details) {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        viewModel = (activity as ProvideViewModel).viewModel(DetailsViewModel::class.java)
        arguments?.getLong("id")?.let { viewModel.init(it) }
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.comeback()
                dismiss()
            }
        }
        (dialog as BottomSheetDialog).onBackPressedDispatcher.addCallback(onBackPressedCallback)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = view.findViewById<TextView>(R.id.itemTextView)
        val input = view.findViewById<TextInputEditText>(R.id.itemInputEditText)
        val delete = view.findViewById<Button>(R.id.deleteButton)
        val save = view.findViewById<Button>(R.id.updateButton)
        viewModel.liveData.observe(this) {
            title.text = it
            input.setText(it)
        }
        delete.setOnClickListener {
            arguments?.getLong("id")?.let { viewModel.delete(it) }
            dismiss()
        }
        input.doAfterTextChanged {
            if (it != null) {
                save.isEnabled = it.length > 2
            }
        }
        save.setOnClickListener {
            arguments?.getLong("id")?.let { viewModel.update(it, input.text.toString()) }
            dismiss()
        }
    }
}