package ru.easycode.zerotoheroandroidtdd.folder.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.databinding.FolderDetailsFragmentBinding

class FolderDetailsFragment: Fragment(R.layout.folder_details_fragment) {
    private lateinit var viewModel: FolderDetailsViewModel
    private var _binding: FolderDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FolderDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                viewModel.comeback()
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ProvideViewModel).viewModel(FolderDetailsViewModel::class.java)
        adapter = NoteAdapter { viewModel.editNote(it) }
        with(binding) {
            notesRecyclerView.adapter = adapter
            addNoteButton.setOnClickListener {
                viewModel.createNote()
            }
            editFolderButton.setOnClickListener {
                viewModel.editFolder()
            }
            viewModel.liveData().observe(viewLifecycleOwner) {
                folderNameTextView.text = it.title
                notesCountTextView.text = it.notesCount.toString()
            }
            viewModel.notesLiveData().observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}