package com.example.a20230710_joshchan_nycschools.usecases

import com.example.a20230710_joshchan_nycschools.model.SchoolItem
import com.example.a20230710_joshchan_nycschools.utils.toSearchPhrase
import javax.inject.Inject

class UcFilterSchoolByName @Inject constructor() {
    operator fun invoke(fullData: List<SchoolItem>, query: String) =
        fullData.filter {
            it.schoolName.toSearchPhrase().contains(query.toSearchPhrase())
        }
}