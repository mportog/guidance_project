package com.mportog.guidanceprojecttest.model.ui

internal data class DataFlow(
    val quote: Quote? = null,
    val status: String? = null,
    val users: List<User> = emptyList()
)
