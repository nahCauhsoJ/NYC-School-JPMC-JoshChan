package com.example.a20230710_joshchan_nycschools.repository

import com.example.a20230710_joshchan_nycschools.model.SATItem
import com.example.a20230710_joshchan_nycschools.model.SchoolItem
import com.example.a20230710_joshchan_nycschools.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface NYCRepository {
    fun getAllSchools(): Flow<ApiResponse<List<SchoolItem>>>
    fun getAllSat(): Flow<ApiResponse<List<SATItem>>>
}