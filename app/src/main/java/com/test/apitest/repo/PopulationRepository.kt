package com.test.apitest.repo

import com.test.apitest.api.ApiService

class PopulationRepository constructor(private val apiService: ApiService) {
    suspend fun usPopulation(drillDowns: String, measures: String) = apiService.population(drillDowns = drillDowns, measures = measures)
}