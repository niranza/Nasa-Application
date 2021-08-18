package com.niran.nasaapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.niran.nasaapplication.R
import com.niran.nasaapplication.databinding.NasaItemBinding
import com.niran.nasaapplication.dataset.models.NasaPicture

class NasaPictureAdapter(
    private val nasaPictureClickHandler: NasaPictureClickHandler
) : ListAdapter<NasaPicture, NasaPictureAdapter.NasaViewHolder>(NasaCallBack) {

    class NasaViewHolder private constructor(
        private val binding: NasaItemBinding,
        private val nasaPictureClickHandler: NasaPictureClickHandler
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(nasa: NasaPicture) = binding.apply {
            ivNasaImage.load(nasa.photoUrl) {
                crossfade(true)
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
            tvNasaTitle.text = nasa.title
            tvNasaDate.text = nasa.date
            tvNasaExplanation.text = nasa.explanation
            itemView.setOnClickListener { nasaPictureClickHandler.onNasaPictureClicked(nasa) }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                nasaPictureClickHandler: NasaPictureClickHandler
            ): NasaViewHolder {
                val binding = NasaItemBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return NasaViewHolder(binding, nasaPictureClickHandler)
            }
        }
    }

    interface NasaPictureClickHandler {
        fun onNasaPictureClicked(nasaPicture: NasaPicture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NasaViewHolder {
        return NasaViewHolder.create(parent, nasaPictureClickHandler)
    }

    override fun onBindViewHolder(holder: NasaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object NasaCallBack : DiffUtil.ItemCallback<NasaPicture>() {
        override fun areItemsTheSame(oldItem: NasaPicture, newItem: NasaPicture): Boolean {
            return newItem.nasaId == oldItem.nasaId
        }

        override fun areContentsTheSame(oldItem: NasaPicture, newItem: NasaPicture): Boolean {
            return oldItem == newItem
        }
    }
}