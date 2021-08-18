package com.niran.nasaapplication.viemwodels

import android.app.Application
import androidx.lifecycle.*
import com.niran.nasaapplication.dataset.models.NasaPicture
import com.niran.nasaapplication.dataset.models.NasaRandomPictures
import com.niran.nasaapplication.repositories.NasaRepository
import com.niran.nasaapplication.utils.Constants
import com.niran.nasaapplication.utils.InternetUtil.Companion.safeRetrofitCall
import com.niran.nasaapplication.utils.Resource
import kotlinx.coroutines.launch

class NasaViewModel(
    private val repository: NasaRepository,
    app: Application
) : AndroidViewModel(app) {

    private val _nasa = MutableLiveData<Resource<NasaRandomPictures>>()
    val nasa: LiveData<Resource<NasaRandomPictures>> get() = _nasa

    init {
        getRandomNasaPictures()
    }

    fun getRandomNasaPictures() = viewModelScope.launch {
        _nasa.safeRetrofitCall(getApplication()) {
            repository.getRandomNasaPictures(Constants.IMAGES_PER_PAGE).apply {
                if (isSuccessful) body()?.let { nasaList ->
                    _nasa.postValue(Resource.Success(nasaList))
                    return@safeRetrofitCall
                }
                _nasa.postValue(Resource.Error(message()))
            }
        }
    }

    val nasaPictureListAsLiveData = repository.savedNasaPictureListWithFlow.asLiveData()

    fun insertNasaPicture(nasaPicture: NasaPicture) =
        viewModelScope.launch { repository.insertNasaPicture(nasaPicture) }

    fun deleteNasaPicture(nasaPicture: NasaPicture) =
        viewModelScope.launch { repository.deleteNasaPicture(nasaPicture) }

    fun deleteAllSavedNasaPictures() =
        viewModelScope.launch { repository.deleteAllSavedNasaPictures() }
}

class NasaViewModelFactory(
    private val repository: NasaRepository,
    private val app: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NasaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NasaViewModel(repository, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}