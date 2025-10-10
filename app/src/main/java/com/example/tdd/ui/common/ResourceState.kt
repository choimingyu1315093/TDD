package com.example.tdd.ui.common

sealed interface ResourceState<out T> {
    object Loading: ResourceState<Nothing>
    data class Success<T>(val data: T): ResourceState<T>
    data class Error(val message: String): ResourceState<Nothing>
}