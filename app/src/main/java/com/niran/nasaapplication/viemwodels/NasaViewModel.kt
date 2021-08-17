package com.niran.nasaapplication.viemwodels

import androidx.lifecycle.*
import com.niran.nasaapplication.dataset.models.NasaRandomPictures
import com.niran.nasaapplication.repositories.NasaRepository
import com.niran.nasaapplication.utils.Constants.Companion.IMAGES_PER_PAGE
import com.niran.nasaapplication.utils.Resource
import kotlinx.coroutines.launch

class NasaViewModel(private val repository: NasaRepository) : ViewModel() {

    private val _nasa = MutableLiveData<Resource<NasaRandomPictures>>()
    val nasa: LiveData<Resource<NasaRandomPictures>> get() = _nasa

    init {
        getNasaRandomPictures()
    }

    fun getNasaRandomPictures() = viewModelScope.launch {
        _nasa.postValue(Resource.Loading())
        repository.getNasaRandomPictures(IMAGES_PER_PAGE).apply {
            if (isSuccessful) body()?.let { nasaList ->
                _nasa.postValue(Resource.Success(nasaList))
                return@launch
            }
            _nasa.postValue(Resource.Error(message()))
        }
    }
}

class NasaViewModelFactory(private val repository: NasaRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NasaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NasaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}