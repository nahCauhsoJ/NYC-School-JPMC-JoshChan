package com.example.a20230710_joshchan_nycschools.mapper

import com.example.a20230710_joshchan_nycschools.dto.SATResponseItem
import com.example.a20230710_joshchan_nycschools.model.SATItem

fun SATResponseItem.toSATItem() =
    SATItem(
        dbn = dbn,
        numOfSatTestTakers = numOfSatTestTakers,
        satCriticalReadingAvgScore = satCriticalReadingAvgScore,
        satMathAvgScore = satMathAvgScore,
        satWritingAvgScore = satWritingAvgScore,
        schoolName = schoolName
    )

fun List<SATResponseItem>.toSATItem() = map{ it.toSATItem() }