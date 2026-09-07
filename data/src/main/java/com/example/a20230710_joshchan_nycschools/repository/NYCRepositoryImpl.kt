package com.example.a20230710_joshchan_nycschools.repository

import com.example.a20230710_joshchan_nycschools.ConstData
import com.example.a20230710_joshchan_nycschools.api.ApiNycData
import com.example.a20230710_joshchan_nycschools.mapper.toSATItem
import com.example.a20230710_joshchan_nycschools.mapper.toSchoolItem
import com.example.a20230710_joshchan_nycschools.model.SATItem
import com.example.a20230710_joshchan_nycschools.model.SchoolItem
import com.example.a20230710_joshchan_nycschools.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

/*
* For Retrofit's Response object, if it's a success, the body() is guaranteed to
*   be non-null. But if an error occurred, body() is 100% null and errorBody()
*   is usually non-null. Hence a safe call on body() should cover all cases.
* As for internet connection and timeouts, which can actually crash the app,
*   it's being handled by interceptors, provided via Hilt.
* */
class NYCRepositoryImpl @Inject constructor(
    private val apiNycData: ApiNycData
): NYCRepository {
    override fun getAllSchools(): Flow<ApiResponse<List<SchoolItem>>> =
        flow {
            // The api call below takes time. Hence the flow should emit a loading state first.
            emit(ApiResponse.Loading())
            try {
                withTimeout(ConstData.TIMEOUT) {
                    with(apiNycData.getAllSchools()) {
                        emit(
                            body()
                                ?.run { ApiResponse.Success(this.toSchoolItem()) }
                                ?: ApiResponse.Error(errorBody()?.string())
                        )
                    }
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message ?: "Unknown Error"))
            }
        }

    override fun getAllSat(): Flow<ApiResponse<List<SATItem>>> =
        flow {
            try {
                withTimeout(ConstData.TIMEOUT) {
                    with(apiNycData.getAllSat()) {
                        emit(
                            body()
                                ?.run { ApiResponse.Success(this.toSATItem()) }
                                ?: ApiResponse.Error(errorBody()?.string())
                        )
                    }
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message ?: "Unknown Error"))
            }
        }
}