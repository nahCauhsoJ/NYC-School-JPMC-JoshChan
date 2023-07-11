package com.example.a20230710_joshchan_nycschools.usecases

import com.example.a20230710_joshchan_nycschools.model.SATItem
import javax.inject.Inject

class UcGetSatByDbn @Inject constructor() {
    operator fun invoke(
        dbn: String,
        satData: List<SATItem>
    ) = satData.firstOrNull{ it.dbn == dbn }
}