package com.example.a20230710_joshchan_nycschools.model

data class SATItem(
    val dbn: String,
    val numOfSatTestTakers: String,
    val satCriticalReadingAvgScore: String,
    val satMathAvgScore: String,
    val satWritingAvgScore: String,
    val schoolName: String
) {
    companion object {
        fun getEmpty() = SATItem(
            dbn = "",
            numOfSatTestTakers = "",
            satCriticalReadingAvgScore = "",
            satMathAvgScore = "",
            satWritingAvgScore = "",
            schoolName = ""
        )
    }
}
