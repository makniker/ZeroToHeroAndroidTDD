package ru.easycode.zerotoheroandroidtdd.note.edit

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.databinding.EditNoteFragmentBinding

class EditNoteFragment : BottomSheetDialogFragment(R.layout.edit_note_fragment) {

    private var _binding: EditNoteFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EditNoteViewModel
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.comeback()
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        viewModel = (activity as ProvideViewModel).viewModel(EditNoteViewModel::class.java)
        (dialog as BottomSheetDialog).onBackPressedDispatcher.addCallback(onBackPressedCallback)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = EditNoteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            arguments?.getLong("noteId")?.let { it1 ->
                viewModel.init(it1)
                saveNoteButton.setOnClickListener {
                    viewModel.renameNote(it1, noteEditText.text.toString())
                    dismiss()
                }
                deleteNoteButton.setOnClickListener {
                    viewModel.deleteNote(it1)
                    dismiss()
                }
            }
            viewModel.liveData().observe(viewLifecycleOwner) {
                noteEditText.setText(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}