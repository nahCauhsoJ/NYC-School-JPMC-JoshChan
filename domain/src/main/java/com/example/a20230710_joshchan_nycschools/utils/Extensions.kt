package com.example.a20230710_joshchan_nycschools.utils

import com.example.a20230710_joshchan_nycschools.model.SchoolItem

// Transformations:
//      1. Make it all lowercase
//      2. Strip all the whitespace
//      3. Take away '.
fun String.toSearchPhrase() =
    this.lowercase()
        .replace(" ", "")
        .replace("'", "")

fun String.fixWeirdChars() = replace("Â","")

fun SchoolItem.getFullAddress() = "$primaryAddressLine1, $city, $stateCode $zip"