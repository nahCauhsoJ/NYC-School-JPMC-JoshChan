package com.example.a20230710_joshchan_nycschools.usecases

import com.example.a20230710_joshchan_nycschools.repository.NYCRepository
import javax.inject.Inject

class UcGetAllSat @Inject constructor(
    private val repo: NYCRepository
) {
    operator fun invoke() = repo.getAllSat()
}