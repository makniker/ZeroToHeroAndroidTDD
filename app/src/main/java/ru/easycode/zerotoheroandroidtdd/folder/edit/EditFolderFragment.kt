package ru.easycode.zerotoheroandroidtdd.folder.edit

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
import ru.easycode.zerotoheroandroidtdd.databinding.EditFolderFragmentBinding

class EditFolderFragment : BottomSheetDialogFragment(R.layout.edit_folder_fragment) {
    private var _binding: EditFolderFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EditFolderViewModel
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.comeback()
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        viewModel = (activity as ProvideViewModel).viewModel(EditFolderViewModel::class.java)
        (dialog as BottomSheetDialog).onBackPressedDispatcher.addCallback(onBackPressedCallback)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EditFolderFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            saveFolderButton.setOnClickListener {
                arguments?.getLong("folderId")?.let { it1 ->
                    viewModel.renameFolder(it1, folderEditText.text.toString())
                    dismiss()
                }
            }
            deleteFolderButton.setOnClickListener {
                arguments?.getLong("folderId")?.let { it1 ->
                    viewModel.deleteFolder(it1)
                    dismiss()
                }
            }
            viewModel.liveData().observe(viewLifecycleOwner) { folderEditText.setText(it.title) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}