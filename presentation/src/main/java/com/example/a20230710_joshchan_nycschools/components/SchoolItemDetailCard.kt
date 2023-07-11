package com.example.a20230710_joshchan_nycschools.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a20230710_joshchan_nycschools.model.SATItem
import com.example.a20230710_joshchan_nycschools.model.SchoolItem
import com.example.a20230710_joshchan_nycschools.utils.getFullAddress
import kotlin.reflect.KFunction0

@Composable
fun SchoolItemDetailCard(
    schoolData: SchoolItem,
    satData: SATItem?,
    modifier: Modifier = Modifier,
    onClose: KFunction0<Unit>,
    onWebsiteClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .then(modifier)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SchoolHeader(schoolData = schoolData)

            SATScores(satData = satData)

            OverviewCard(schoolData.overviewParagraph)

            ContactCard(schoolData = schoolData, onWebsiteClick = onWebsiteClick)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.Black.copy(alpha = 0.3f)
                )
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Button({ onClose() }) {
                Text("Close")
            }
        }
    }
}

@Composable
private fun SchoolHeader(
    schoolData: SchoolItem
) {
    Column (
        Modifier.fillMaxWidth().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(schoolData.schoolName, style = MaterialTheme.typography.titleMedium)
        schoolData.campusName?.let {
            Text("($it)", style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
private fun SATScores(satData: SATItem?) {
    Card(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            satData?.let {
                Text("SAT Score Average", fontWeight = FontWeight.Bold)
                Text("(${satData.numOfSatTestTakers} students took the test)")
                Spacer(Modifier.height(4.dp))
                Text("Math: ${satData.satMathAvgScore} / 800")
                Text("Reading: ${satData.satCriticalReadingAvgScore} / 800")
                Text("Writing: ${satData.satWritingAvgScore} / 800")
            } ?: Text("(SAT score unavailable...)")
        }

    }
}

@Composable
private fun OverviewCard(
    overview: String
) {
    Card(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Overview", fontWeight = FontWeight.Bold)
            Text(overview)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContactCard(
    schoolData: SchoolItem,
    onWebsiteClick: (String) -> Unit
) {
    Card(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Contact Us", fontWeight = FontWeight.Bold)
            Text("Phone: ${schoolData.phoneNumber}")
            schoolData.faxNumber?.let{ Text("Fax: $it") }
            schoolData.schoolEmail?.let { Text("Email: $it") }
            Row(
                modifier = Modifier.wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically) {
                Text("Website: ")

                CompositionLocalProvider(
                    LocalMinimumInteractiveComponentEnforcement provides false,
                ) {
                    TextButton(
                        onClick = { onWebsiteClick(schoolData.website) },
                        contentPadding = PaddingValues(),
                        modifier = Modifier.defaultMinSize(1.dp,1.dp)
                    ) {
                        Text(schoolData.website)
                    }
                }
            }
            Text("Address: ${schoolData.getFullAddress()}")
        }
    }
}

@Preview
@Composable
private fun SchoolItemDetailCardPreview() {
    SchoolItemDetailCard(
        schoolData = SchoolItem.getEmpty().copy(
            schoolName = "Test Test Academy",
            overviewParagraph = "This school is somewhere you want to go. Trust me bro. We get no sport programmer but we do not require you to study any history.",
            phoneNumber = "(123) 459 7890",
            faxNumber = "(123) 456 7890",
            website = "www.google.com",
            primaryAddressLine1 = "1234 Street",
            city = "City",
            stateCode = "AB",
            zip = "12345",
            schoolEmail = "abc@def.edu"
        ),
        satData = SATItem.getEmpty().copy(
            numOfSatTestTakers = "69",
            satCriticalReadingAvgScore = "369",
            satMathAvgScore = "420",
            satWritingAvgScore = "387"
        ),
        modifier = Modifier.fillMaxSize(),
        onClose = ::placeholderFunc1
    ) {}
}

private fun placeholderFunc1() {}