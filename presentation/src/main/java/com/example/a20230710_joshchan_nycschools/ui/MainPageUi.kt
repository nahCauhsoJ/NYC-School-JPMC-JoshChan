package com.example.a20230710_joshchan_nycschools.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.a20230710_joshchan_nycschools.R
import com.example.a20230710_joshchan_nycschools.components.SchoolItemBriefCard
import com.example.a20230710_joshchan_nycschools.components.SchoolItemDetailCard
import com.example.a20230710_joshchan_nycschools.model.SchoolItem
import com.example.a20230710_joshchan_nycschools.utils.ApiResponse
import com.example.a20230710_joshchan_nycschools.viewmodels.MainPageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPageUi(
    mainPageViewModel: MainPageViewModel
) {
    val context = LocalContext.current

    // This only needs to be run once in the whole app lifetime,
    //      and it's unnecessary to refresh this during it.
    //      Hence, a one-time LaunchEffect in the UI will suffice.
    // This is required over an init block in the view model cuz a race condition
    //      can occur when accessing mutable state during view model's construction.
    //      It also makes testing easier since init block in view model means a
    //      certain testable function is forced to run on every creation.
    LaunchedEffect(Unit) {
        mainPageViewModel.initializeSchoolData()
    }



    SearchBar(
        query = mainPageViewModel.schoolSearchQuery,
        onQueryChange = { mainPageViewModel.searchSchoolName(it, false) },
        onSearch = { mainPageViewModel.searchSchoolName(it, true) },
        active = mainPageViewModel.schoolsDataFiltered !is ApiResponse.Loading<*>,
        onActiveChange = {},
        leadingIcon = { Icon(painterResource(R.drawable.baseline_search_24), "") },
        placeholder = { Text("Find a school") }
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "High Schools in NYC",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.titleLarge
            )

            // The extra box here is to center the error message and the loading circle.
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                when (val data = mainPageViewModel.schoolsDataFiltered) {
                    is ApiResponse.Error -> {
                        Text("Error loading the list of schools...")
                    }
                    is ApiResponse.Loading -> {
                        CircularProgressIndicator(
                            Modifier.size(256.dp),
                            strokeWidth = 16.dp
                        )
                    }
                    is ApiResponse.Null -> {}
                    is ApiResponse.Success -> {
                        PageContentIfSuccess(data = data.body) {
                            mainPageViewModel.selectSchool(it)
                        }
                    }
                }
            }
        }
    }

    mainPageViewModel.currentSelectedSchool?.let {
        BackHandler{ mainPageViewModel.unselectSchool() }

        SchoolItemDetailCard(
            it,
            mainPageViewModel.findSchoolSAT(it),
            Modifier
                .fillMaxSize()
                .zIndex(1f),
            mainPageViewModel::unselectSchool
        ) { url ->
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://$url")))
        }
    }
}

@Composable
private fun PageContentIfSuccess(
    data: List<SchoolItem>,
    onItemClick: (SchoolItem) -> Unit
) {
    LazyColumn(Modifier.fillMaxWidth()) {
        itemsIndexed(
            data,
            key = { _, it -> it.dbn }
        ) {i, it ->
            Column{
                SchoolItemBriefCard(data = it, onItemClick = onItemClick)
                if (i < data.lastIndex) Divider(Modifier.padding(vertical = 8.dp))
            }
        }
    }
}