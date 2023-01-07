package com.test.apitest.models

data class Populations(
    val `data`: List<Data>,
    val source: List<Source>
) {
    override fun toString(): String {
        return "$data == $source"
    }
}