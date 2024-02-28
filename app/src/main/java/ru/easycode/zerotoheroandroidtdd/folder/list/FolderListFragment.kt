package ru.easycode.zerotoheroandroidtdd.folder.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.databinding.FoldersListFragmentBinding
import ru.easycode.zerotoheroandroidtdd.main.MainActivity

class FolderListFragment: Fragment() {
    private var _binding : FoldersListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FolderListViewModel
    private lateinit var adapter: FolderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FoldersListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel(FolderListViewModel::class.java)
        viewModel.init()
        with(binding) {
            adapter = FolderAdapter() {
                viewModel.folderDetails(it)
            }
            foldersRecyclerView.adapter = adapter
            addButton.setOnClickListener {
                viewModel.addFolder()
            }
            viewModel.liveData().observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}