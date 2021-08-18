package com.niran.nasaapplication.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.niran.nasaapplication.R
import com.niran.nasaapplication.adapters.NasaPictureAdapter
import com.niran.nasaapplication.databinding.FragmentSavedNasaPicturesBinding
import com.niran.nasaapplication.dataset.models.NasaPicture
import com.niran.nasaapplication.utils.FragmentUtils.Companion.nasaViewModel
import com.niran.nasaapplication.utils.FragmentUtils.Companion.showUndoSnackBar
import com.niran.nasaapplication.viemwodels.NasaViewModel


class SavedNasaPicturesFragment : Fragment() {

    private var _binding: FragmentSavedNasaPicturesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NasaViewModel by lazy { nasaViewModel() }

    private lateinit var nasaPictureAdapter: NasaPictureAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSavedNasaPicturesBinding.inflate(inflater)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            nasaPictureAdapter =
                NasaPictureAdapter(object : NasaPictureAdapter.NasaPictureClickHandler {
                    override fun onNasaPictureClicked(nasaPicture: NasaPicture) {
                        navigateToNasaPictureFragment(nasaPicture)
                    }
                })

            rvSavedNasa.also {
                it.adapter = nasaPictureAdapter
                touchHelper.attachToRecyclerView(it)
            }

            viewModel.nasaPictureListAsLiveData.observe(viewLifecycleOwner) { nasaList ->
                nasaList?.let {
                    if (it.isEmpty()) showNoImages() else hideNoImages()
                    nasaPictureAdapter.submitList(it)
                }
            }
        }
    }

    private fun showNoImages() = binding.apply { tvNoImages.visibility = View.VISIBLE }

    private fun hideNoImages() = binding.apply { tvNoImages.visibility = View.GONE }

    private fun navigateToNasaPictureFragment(nasaPicture: NasaPicture) =
        view?.findNavController()?.navigate(
            SavedNasaPicturesFragmentDirections
                .actionSavedNasaPicturesFragmentToNasaPictureFragment(nasaPicture, TAG)
        )

    private val touchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.DOWN or ItemTouchHelper.UP,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            nasaPictureAdapter.currentList[viewHolder.adapterPosition].also { nasaPicture ->
                viewModel.deleteNasaPicture(nasaPicture)
                showUndoSnackBar { viewModel.insertNasaPicture(nasaPicture) }
            }
        }
    })

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) =
        inflater.inflate(R.menu.saved_nasa_pictures_menu, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.item_delete_all_pictures -> {
            if (nasaPictureAdapter.currentList.isNotEmpty()) showDeleteAllPicturesDialog()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun showDeleteAllPicturesDialog() = AlertDialog.Builder(requireContext()).apply {
        setTitle(R.string.delete_all_pictures_title)
        setPositiveButton(R.string.yes) { _, _ -> viewModel.deleteAllSavedNasaPictures() }
        setNegativeButton(R.string.no) { _, _ -> }
        show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "SavedNasaPicturesFragment"
    }
}