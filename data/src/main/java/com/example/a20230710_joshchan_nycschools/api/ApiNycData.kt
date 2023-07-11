package com.example.a20230710_joshchan_nycschools.api

import com.example.a20230710_joshchan_nycschools.ConstData
import com.example.a20230710_joshchan_nycschools.dto.SATResponseItem
import com.example.a20230710_joshchan_nycschools.dto.SchoolsResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiNycData {
    @GET(ConstData.SCHOOLS_ENDPOINT)
    suspend fun getAllSchools(): Response<List<SchoolsResponseItem>>

    @GET(ConstData.SAT_ENDPOINT)
    suspend fun getAllSat(): Response<List<SATResponseItem>>
}