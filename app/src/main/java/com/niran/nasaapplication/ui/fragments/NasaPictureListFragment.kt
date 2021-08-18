package com.niran.nasaapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.niran.nasaapplication.R
import com.niran.nasaapplication.adapters.NasaPictureAdapter
import com.niran.nasaapplication.databinding.FragmentNasaListBinding
import com.niran.nasaapplication.dataset.models.NasaPicture
import com.niran.nasaapplication.utils.FragmentUtils.Companion.nasaViewModel
import com.niran.nasaapplication.utils.FragmentUtils.Companion.showSwipeToRefreshSnackBar
import com.niran.nasaapplication.utils.Resource
import com.niran.nasaapplication.utils.SharedPrefUtil.Companion.getSharedPrefBoolean
import com.niran.nasaapplication.utils.SharedPrefUtil.Companion.setSharedPrefBoolean


class NasaPictureListFragment : Fragment() {

    private var _binding: FragmentNasaListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy { nasaViewModel() }

    private var showSwipePopUp = false

    private var swipeToRefreshSnackBar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNasaListBinding.inflate(inflater)

        loadShowSwipePopUp()

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

            layoutRefreshPictures.setOnRefreshListener {
                viewModel.getNasaRandomPictures()
                saveShowSwipePopUp(false)
            }

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
                            nasaPictureAdapter.submitList(nasaList)
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

    private fun showSwipeToRefreshPopUp() {
        if (showSwipePopUp) {
            swipeToRefreshSnackBar = showSwipeToRefreshSnackBar { saveShowSwipePopUp(false) }
        }
    }

    private fun hideSwipeSnackBar() = swipeToRefreshSnackBar?.dismiss()

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

    private fun loadShowSwipePopUp() = requireContext().getSharedPrefBoolean(
        R.string.nasa_picture_list_fragment_file_key,
        R.string.show_swipe_snack_bar_pref_key,
        true
    ).also { showSwipePopUp = it }

    @Suppress("SameParameterValue")
    private fun saveShowSwipePopUp(boolean: Boolean) {
        if (showSwipePopUp == boolean) return
        requireContext().setSharedPrefBoolean(
            R.string.nasa_picture_list_fragment_file_key,
            R.string.show_swipe_snack_bar_pref_key,
            boolean
        ).also { showSwipePopUp = boolean }
        hideSwipeSnackBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "NasaListFragment"
    }
}