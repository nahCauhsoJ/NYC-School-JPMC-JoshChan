package com.example.a20230710_joshchan_nycschools.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.a20230710_joshchan_nycschools.model.SchoolItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SchoolItemBriefCard(
    data: SchoolItem,
    onItemClick: (SchoolItem) -> Unit
) {
    Column(
        Modifier.clickable{ onItemClick(data) }
    ) {
        // The marquee doesn't apply if there's no overflow.
        Text(
            text = data.schoolName,
            modifier = Modifier.basicMarquee(),
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
        data.campusName?.let{
            Text(
                "($it)",
                modifier = Modifier.basicMarquee(),
                maxLines = 1
            )
        }

        Text(
            "${data.primaryAddressLine1}, ${data.city}",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = data.overviewParagraph,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}