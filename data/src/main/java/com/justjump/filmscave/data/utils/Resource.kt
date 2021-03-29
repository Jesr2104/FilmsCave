package com.justjump.filmscave.data.utils

data class Resource<out T>(val status: Status, val codeException: String?) {

    companion object {
        fun <T> success(): Resource<T> {
            return Resource(Status.SUCCESS, null)
        }

        fun <T> error(codeException: String): Resource<T> {
            return Resource(Status.ERROR, codeException)
        }
    }
}