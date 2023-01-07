package com.test.apitest.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.test.apitest.models.Populations
import com.test.apitest.repo.PopulationRepository
import com.test.apitest.utils.NetworkStatusTracker
import com.test.apitest.utils.map
import kotlinx.coroutines.*

class PopulationViewModel constructor(private val populationRepository: PopulationRepository,private val networkStatusTracker: NetworkStatusTracker): ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val usPopulations = MutableLiveData<Populations>()
    val dataAvailable = MutableLiveData<Boolean>()
    private val coroutineExceptionHandler =  CoroutineExceptionHandler { _, throwable ->
        throwable.localizedMessage?.let { error(it) }
    }
    private var job: Job? = null

    val state = networkStatusTracker.networkStatus.map(onAvailable = {
        NetState.Fetched
    }, onUnavailable = {
        NetState.Error
    }).asLiveData(Dispatchers.IO)

    fun usPopulation() {
        job = viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            populationRepository.usPopulation("Nation","Population").also { response ->
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        usPopulations.postValue(response.body())
                        dataAvailable.postValue(true)
                    } else error(response.message())
                }
            }
        }
    }

    private fun error(error: String) {
        errorMessage.postValue(error)
        dataAvailable.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    sealed class NetState {
        object Fetched : NetState()
        object Error : NetState()
    }
}