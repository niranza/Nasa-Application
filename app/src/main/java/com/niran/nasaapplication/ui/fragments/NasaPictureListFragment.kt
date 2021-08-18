package com.niran.nasaapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.niran.nasaapplication.R
import com.niran.nasaapplication.adapters.NasaPictureAdapter
import com.niran.nasaapplication.databinding.FragmentNasaListBinding
import com.niran.nasaapplication.dataset.models.NasaPicture
import com.niran.nasaapplication.utils.FragmentUtils.Companion.nasaViewModel
import com.niran.nasaapplication.utils.FragmentUtils.Companion.showSwipeToRefreshSnackBar
import com.niran.nasaapplication.utils.Resource


class NasaPictureListFragment : Fragment() {

    private var _binding: FragmentNasaListBinding? = null
    private val binding get() = _binding!!

    private var swipePopUpShowed = false

    private val viewModel by lazy { nasaViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNasaListBinding.inflate(inflater)

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

            rvNasa.adapter = nasaPictureAdapter

            layoutRefreshPictures.setOnRefreshListener { viewModel.getNasaRandomPictures() }

            viewModel.nasa.observe(viewLifecycleOwner) { nasaListResponse ->
                when (nasaListResponse) {
                    is Resource.Loading -> {
                        layoutRefreshPictures.isRefreshing = false
                        showLoadingProgressBar()
                    }
                    is Resource.Success -> {
                        hideLoadingProgressBar()
                        showSwipeToRefreshPopUp()
                        nasaListResponse.data?.let { nasaList ->
                            nasaPictureAdapter.submitList(
                                nasaList
                            )
                        }
                    }
                    is Resource.Error -> {
                        hideLoadingProgressBar()
                        Log.e(TAG, getString(R.string.error_loading_nasa, nasaListResponse.message))
                    }
                }
            }
        }
    }

    private fun showSwipeToRefreshPopUp() = binding.apply {
        if (!swipePopUpShowed) {
            showSwipeToRefreshSnackBar()
            swipePopUpShowed = true
        }
    }

    private fun hideLoadingProgressBar() = binding.apply {
        pbLoading.visibility = View.GONE
        rvNasa.visibility = View.VISIBLE
    }

    private fun showLoadingProgressBar() = binding.apply {
        pbLoading.visibility = View.VISIBLE
        rvNasa.visibility = View.GONE
    }

    private fun navigateToNasaPictureFragment(nasaPicture: NasaPicture) =
        view?.findNavController()?.navigate(
            NasaPictureListFragmentDirections
                .actionNasaListFragmentToNasaPictureFragment(nasaPicture, null)
        )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "NasaListFragment"
    }
}