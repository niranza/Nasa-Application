package com.niran.nasaapplication.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.niran.nasaapplication.R
import com.niran.nasaapplication.databinding.FragmentImagePreviewBinding


class ImagePreviewFragment : Fragment() {

    private var _binding: FragmentImagePreviewBinding? = null
    private val binding get() = _binding!!

    private val args: ImagePreviewFragmentArgs by navArgs()
    private val imageUrl get() = args.imageUrl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentImagePreviewBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            ivNasaImage.apply {
                load(imageUrl) {
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.ic_broken_image)
                    crossfade(true)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    transitionName = getString(R.string.image_big_transition_name)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}