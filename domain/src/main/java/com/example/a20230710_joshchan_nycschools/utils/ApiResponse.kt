package com.example.a20230710_joshchan_nycschools.utils

sealed class ApiResponse<T>{
    class Null<T>: ApiResponse<T>()
    class Loading<T>: ApiResponse<T>()
    data class Success<T>(val body: T): ApiResponse<T>()
    data class Error<T>(val error: String?): ApiResponse<T>()
}
