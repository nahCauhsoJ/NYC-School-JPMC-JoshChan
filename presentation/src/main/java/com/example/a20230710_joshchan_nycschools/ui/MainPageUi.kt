package com.example.a20230710_joshchan_nycschools.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainPageUi(
    mainPageViewModel: MainPageViewModel
) {
    val context = LocalContext.current
    val pullRefreshState = rememberPullRefreshState(
        refreshing = mainPageViewModel.isRefreshing,
        onRefresh = { mainPageViewModel.refreshData() }
    )

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
                    .weight(1f)
                    .pullRefresh(pullRefreshState),
                contentAlignment = Alignment.TopCenter
            ) {
                when (val data = mainPageViewModel.schoolsDataFiltered) {
                    is ApiResponse.Error -> {
                        LazyColumn(Modifier.fillMaxSize()) {
                            item {
                                Box(
                                    Modifier.fillParentMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Error loading the list of schools...")
                                }
                            }
                        }
                    }
                    is ApiResponse.Loading -> {
                        // Handled by LoadingOverlay
                    }
                    is ApiResponse.Null -> {
                        LazyColumn(Modifier.fillMaxSize()) {
                            item { Box(Modifier.fillParentMaxSize()) }
                        }
                    }
                    is ApiResponse.Success -> {
                        PageContentIfSuccess(data = data.body) {
                            mainPageViewModel.selectSchool(it)
                        }
                    }
                }

                PullRefreshIndicator(
                    refreshing = mainPageViewModel.isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }

    if (mainPageViewModel.schoolsDataFiltered is ApiResponse.Loading) {
        LoadingOverlay()
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

@Composable
fun LoadingOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .zIndex(10f)
            .clickable(enabled = false) { },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
