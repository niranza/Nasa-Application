package com.niran.nasaapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.niran.nasaapplication.adapters.NasaPictureAdapter
import com.niran.nasaapplication.databinding.FragmentSavedNasaPicturesBinding
import com.niran.nasaapplication.dataset.models.NasaPicture
import com.niran.nasaapplication.utils.FragmentUtils.Companion.nasaViewModel


class SavedNasaPicturesFragment : Fragment() {

    private var _binding: FragmentSavedNasaPicturesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy { nasaViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSavedNasaPicturesBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            val nasaPictureAdapter =
                NasaPictureAdapter(object : NasaPictureAdapter.NasaPictureClickHandler {
                    override fun onNasaPictureClicked(nasaPicture: NasaPicture) {
                        navigateToNasaPictureFragment(nasaPicture)
                    }
                })

            rvSavedNasa.adapter = nasaPictureAdapter

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val TAG = "SavedNasaPicturesFragment"
    }
}