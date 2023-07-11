package com.example.a20230710_joshchan_nycschools.model

import com.example.a20230710_joshchan_nycschools.utils.fixWeirdChars

data class SchoolItem(
    val addtlInfo1: String?,
    val advancedplacementCourses: String?,
    val attendanceRate: String,
    val boys: String?,
    val campusName: String?,
    val city: String,
    val collegeCareerRate: String?,
    val dbn: String,
    val endTime: String?,
    val extracurricularActivities: String?,
    val faxNumber: String?,
    val geoeligibility: String?,
    val girls: String?,
    val graduationRate: String?,
    val international: String?,
    val latitude: String?,
    val location: String,
    val longitude: String?,
    val overviewParagraph: String,
    val pctStuSafe: String?,
    val phoneNumber: String,
    val primaryAddressLine1: String,
    val schoolEmail: String?,
    val schoolName: String,
    val schoolSports: String?,
    val stateCode: String,
    val totalStudents: String,
    val transfer: String?,
    val website: String,
    val zip: String
) {
    // This is mainly for testing purposes. use .copy() to add data.
    companion object {
        fun getEmpty() = SchoolItem(
            addtlInfo1 = null,
            advancedplacementCourses = null,
            attendanceRate = "",
            boys = null,
            campusName = null,
            city = "",
            collegeCareerRate = null,
            dbn = "",
            endTime = null,
            extracurricularActivities = null,
            faxNumber = null,
            geoeligibility = null,
            girls = null,
            graduationRate = null,
            international = null,
            latitude = null,
            location = "",
            longitude = null,
            overviewParagraph = "",
            pctStuSafe = null,
            phoneNumber = "",
            primaryAddressLine1 = "",
            schoolEmail = null,
            schoolName = "",
            schoolSports = null,
            stateCode = "",
            totalStudents = "",
            transfer = null,
            website = "",
            zip = ""
        )
    }
}