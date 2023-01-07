package com.test.apitest.models

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("ID Nation")
    val idNation: String,
    @SerializedName("ID Year")
    val idYear: Int,
    @SerializedName("Nation")
    val nation: String,
    @SerializedName("Population")
    val population: Int,
    @SerializedName("Slug Nation")
    val slugNation: String,
    @SerializedName("Year")
    val year: String
) {
    override fun toString(): String {
        return "idNation: $idNation,\nidYear: $idYear,\nnation: $nation,\npopulation: $population,\nslugNation: $slugNation,\nyear: $year"
    }
}