package com.test.apitest.models

import com.test.apitest.models.Annotations

data class Source(
    val annotations: Annotations,
    val measures: List<String>,
    val name: String,
    val substitutions: List<Any>
) {
    override fun toString(): String {
        return "$annotations:: $measures"
    }
}