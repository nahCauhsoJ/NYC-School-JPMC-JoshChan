package com.example.a20230710_joshchan_nycschools.mapper

import com.example.a20230710_joshchan_nycschools.dto.SchoolsResponseItem
import com.example.a20230710_joshchan_nycschools.model.SchoolItem
import com.example.a20230710_joshchan_nycschools.utils.fixWeirdChars

fun SchoolsResponseItem.toSchoolItem() = 
    SchoolItem(
        addtlInfo1 = addtlInfo1,
        advancedplacementCourses = advancedplacementCourses,
        attendanceRate = attendanceRate,
        boys = boys,
        campusName = campusName,
        city = city,
        collegeCareerRate = collegeCareerRate,
        dbn = dbn,
        endTime = endTime,
        extracurricularActivities = extracurricularActivities,
        faxNumber = faxNumber,
        geoeligibility = geoeligibility,
        girls = girls,
        graduationRate = graduationRate,
        international = international,
        latitude = latitude,
        location = location,
        longitude = longitude,
        overviewParagraph = overviewParagraph.fixWeirdChars(),
        pctStuSafe = pctStuSafe,
        phoneNumber = phoneNumber,
        primaryAddressLine1 = primaryAddressLine1,
        schoolEmail = schoolEmail,
        schoolName = schoolName,
        schoolSports = schoolSports,
        stateCode = stateCode,
        totalStudents = totalStudents,
        transfer = transfer,
        website = website,
        zip = zip
    )

fun List<SchoolsResponseItem>.toSchoolItem() = map { it.toSchoolItem() }