package com.test.apitest.viewModels.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.apitest.repo.PopulationRepository
import com.test.apitest.utils.NetworkStatusTracker
import com.test.apitest.viewModels.PopulationViewModel

class PopulationViewModelFactory constructor(private val populationRepository: PopulationRepository,private val networkStatusTracker: NetworkStatusTracker): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PopulationViewModel(populationRepository,networkStatusTracker) as T
    }
}