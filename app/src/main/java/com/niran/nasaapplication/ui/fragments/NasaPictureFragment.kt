package com.niran.nasaapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.niran.nasaapplication.R
import com.niran.nasaapplication.databinding.FragmentNasaPictureBinding
import com.niran.nasaapplication.utils.FragmentUtils.Companion.nasaViewModel


class NasaPictureFragment : Fragment() {

    private var _binding: FragmentNasaPictureBinding? = null
    private val binding get() = _binding!!

    private val navArgs: NasaPictureFragmentArgs by navArgs()
    private val nasaPicture get() = navArgs.nasaPicture
    private val fragmentTag get() = navArgs.fragmentTag

    private val viewModel by lazy { nasaViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNasaPictureBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            when (fragmentTag) {
                SavedNasaPicturesFragment.TAG -> hideSavedFab()
                else -> showSavedFab()
            }

            ivNasaImage.load(nasaPicture.hdPhotoUrl) {
                error(R.drawable.ic_broken_image)
                placeholder(R.drawable.loading_animation)
                crossfade(true)
            }
            tvNasaTitle.text = nasaPicture.title
            tvNasaDate.text = nasaPicture.date
            tvNasaExplanation.text = nasaPicture.explanation
        }
    }

    private fun showSavedFab() = binding.fabSaveImage.apply {
        visibility = View.VISIBLE
        setOnClickListener {
            viewModel.insertNasaPicture(nasaPicture)
            showSavedSuccessfullySnackBar()
        }
    }

    private fun hideSavedFab() = binding.apply { fabSaveImage.visibility = View.GONE }

    private fun showSavedSuccessfullySnackBar() = binding.apply {
        Snackbar.make(root, R.string.saved_successfully, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}