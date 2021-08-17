package com.niran.nasaapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.niran.nasaapplication.NasaApplication
import com.niran.nasaapplication.R
import com.niran.nasaapplication.adapters.NasaAdapter
import com.niran.nasaapplication.databinding.FragmentNasaListBinding
import com.niran.nasaapplication.utils.FragmentUtils.Companion.showSwipeToRefreshSnackBar
import com.niran.nasaapplication.utils.Resource
import com.niran.nasaapplication.viemwodels.NasaViewModel
import com.niran.nasaapplication.viemwodels.NasaViewModelFactory


class NasaListFragment : Fragment() {

    private var _binding: FragmentNasaListBinding? = null
    private val binding get() = _binding!!

    private var swipePopUpShowed = false

    private val viewModel: NasaViewModel by viewModels {
        NasaViewModelFactory((activity?.application as NasaApplication).nasaRepository)
    }

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

            val nasaAdapter = NasaAdapter()

            rvNasa.adapter = nasaAdapter

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
                        nasaListResponse.data?.let { nasaList -> nasaAdapter.submitList(nasaList) }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "NasaListFragment"
    }
}