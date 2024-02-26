package ru.easycode.zerotoheroandroidtdd.presentation.delete

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.presentation.ProvideViewModel

class DeleteFragment : BottomSheetDialogFragment(R.layout.fragment_delete) {

    private lateinit var viewModel: DeleteViewModel
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        viewModel = (activity as ProvideViewModel).viewModel(DeleteViewModel::class.java)
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
        return inflater.inflate(R.layout.fragment_delete, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = view.findViewById<TextView>(R.id.itemTitleTextView)
        val button = view.findViewById<Button>(R.id.deleteButton)
        viewModel.liveData.observe(this) {
            title.text = it
        }
        button.setOnClickListener {
            arguments?.getLong("id")?.let { viewModel.delete(it) }
            dismiss()
        }
    }
}