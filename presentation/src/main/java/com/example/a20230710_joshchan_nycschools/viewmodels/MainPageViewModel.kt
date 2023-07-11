package com.example.a20230710_joshchan_nycschools.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a20230710_joshchan_nycschools.model.SATItem
import com.example.a20230710_joshchan_nycschools.model.SchoolItem
import com.example.a20230710_joshchan_nycschools.usecases.UcFilterSchoolByName
import com.example.a20230710_joshchan_nycschools.usecases.UcGetAllSat
import com.example.a20230710_joshchan_nycschools.usecases.UcGetAllSchools
import com.example.a20230710_joshchan_nycschools.usecases.UcGetSatByDbn
import com.example.a20230710_joshchan_nycschools.utils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val ucGetAllSchools: UcGetAllSchools,
    private val ucGetAllSat: UcGetAllSat,
    private val ucFilterSchoolByName: UcFilterSchoolByName,
    private val ucGetSatByDbn: UcGetSatByDbn,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    // This school data pretty much doesn't update once it gets its data,
    //      so there really isn't a need to make it a StateFlow and
    //      convert to a mutable state in the UI.
    private var schoolsData by mutableStateOf<ApiResponse<List<SchoolItem>>>(ApiResponse.Null())
    private var satData by mutableStateOf<ApiResponse<List<SATItem>>>(ApiResponse.Null())

    // This is the school list filtered by schoolSearchQuery,
    //      and the one being used by the UI.
    var schoolsDataFiltered by mutableStateOf<ApiResponse<List<SchoolItem>>>(ApiResponse.Null())
        private set

    // This is mainly for the search bar in the UI. Filter will be applied
    //      after debounced search and reflected on schoolsDataFiltered.
    var schoolSearchQuery by mutableStateOf("")
        private set

    private var schoolSearchDebounceJob: Job? = null

    var currentSelectedSchool by mutableStateOf<SchoolItem?>(null)
        private set

    fun initializeSchoolData() {
        viewModelScope.launch(ioDispatcher) {
            ucGetAllSchools.invoke().collect{
                // The usecase should run on a UI thread, but the data below
                //      needs to be updated on a main thread.
                withContext(Dispatchers.Main) { schoolsData = it }
                finalizeSearch()
            }

            ucGetAllSat.invoke().collect{
                withContext(Dispatchers.Main) { satData = it }
            }
        }
    }

    // This one will be used by both query change AND when user presses Enter.
    //      However, if user manually presses Enter, it will bypass the
    //      debounce instead of making them wait. Otherwise user might
    //      find the search bar not responsive.
    // Default debounce time is 1.5s. Subject to change.
    fun searchSchoolName(query: String, immediate: Boolean) {
        schoolSearchQuery = query
        if (immediate) finalizeSearch()
        else {
            // This is the debounce search. It's basically just cancelling and
            //      restarting the job if called within the timer. Otherwise
            //      the content will run and set the job to null.
            schoolSearchDebounceJob?.cancel()
            schoolSearchDebounceJob = viewModelScope.launch {
                delay(1500)
                finalizeSearch() // The job will set to null in this function.
            }
        }
    }

    private fun finalizeSearch() {
        // Just in case the timer is still running when the search is immediate.
        schoolSearchDebounceJob?.cancel()
        schoolSearchDebounceJob = null

        // currentData is made to satisfy the impossible smart cast warning.
        val currentData = schoolsData
        schoolsDataFiltered = if (currentData is ApiResponse.Success)
            ApiResponse.Success(ucFilterSchoolByName(currentData.body, schoolSearchQuery))
        else schoolsData
    }

    fun selectSchool(data: SchoolItem) {
        currentSelectedSchool = data
    }

    fun unselectSchool() {
        currentSelectedSchool = null
    }

    fun findSchoolSAT(data: SchoolItem) =
        ucGetSatByDbn(
            data.dbn,
            (satData as? ApiResponse.Success)?.body ?: listOf()
        )
}