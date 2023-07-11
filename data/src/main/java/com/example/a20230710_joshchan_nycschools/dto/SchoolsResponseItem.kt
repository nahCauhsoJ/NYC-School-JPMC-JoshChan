package com.example.a20230710_joshchan_nycschools.dto

import com.google.gson.annotations.SerializedName

data class SchoolsResponseItem(
    @SerializedName("addtl_info1")
    val addtlInfo1: String?,
    @SerializedName("advancedplacement_courses")
    val advancedplacementCourses: String?,
    @SerializedName("attendance_rate")
    val attendanceRate: String,
    @SerializedName("boys")
    val boys: String?,
    @SerializedName("campus_name")
    val campusName: String?,
    @SerializedName("city")
    val city: String,
    @SerializedName("college_career_rate")
    val collegeCareerRate: String?,
    @SerializedName("dbn")
    val dbn: String,
    @SerializedName("end_time")
    val endTime: String?,
    @SerializedName("extracurricular_activities")
    val extracurricularActivities: String?,
    @SerializedName("fax_number")
    val faxNumber: String?,
    @SerializedName("geoeligibility")
    val geoeligibility: String?,
    @SerializedName("girls")
    val girls: String?,
    @SerializedName("graduation_rate")
    val graduationRate: String?,
    @SerializedName("international")
    val international: String?,
    @SerializedName("latitude")
    val latitude: String?,
    @SerializedName("location")
    val location: String,
    @SerializedName("longitude")
    val longitude: String?,
    @SerializedName("overview_paragraph")
    val overviewParagraph: String,
    @SerializedName("pct_stu_safe")
    val pctStuSafe: String?,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("primary_address_line_1")
    val primaryAddressLine1: String,
    @SerializedName("school_email")
    val schoolEmail: String?,
    @SerializedName("school_name")
    val schoolName: String,
    @SerializedName("school_sports")
    val schoolSports: String?,
    @SerializedName("state_code")
    val stateCode: String,
    @SerializedName("total_students")
    val totalStudents: String,
    @SerializedName("transfer")
    val transfer: String?,
    @SerializedName("website")
    val website: String,
    @SerializedName("zip")
    val zip: String
)