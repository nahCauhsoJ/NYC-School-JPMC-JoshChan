package com.example.a20230710_joshchan_nycschools.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.a20230710_joshchan_nycschools.model.SchoolItem
import com.example.a20230710_joshchan_nycschools.usecases.UcFilterSchoolByName
import com.example.a20230710_joshchan_nycschools.usecases.UcGetAllSat
import com.example.a20230710_joshchan_nycschools.usecases.UcGetAllSchools
import com.example.a20230710_joshchan_nycschools.usecases.UcGetSatByDbn
import com.example.a20230710_joshchan_nycschools.utils.ApiResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainPageViewModelTest {
    private lateinit var viewModel: MainPageViewModel
    private val ucGetAllSchools = mockk<UcGetAllSchools>()
    private val ucGetAllSat = mockk<UcGetAllSat>()
    private val ucFilterSchoolByName = mockk<UcFilterSchoolByName>()
    private val ucGetSatByDbn = mockk<UcGetSatByDbn>()
    private val ioDispatcher = Dispatchers.IO

    @JvmField
    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        // Mockk requires all mock objects to return a value, whether needed or not.
        //      Hence, here are some default every{} it'll return. If the test requires
        //      another result, just write the every{} block as usual to overwrite it.
        every { ucGetAllSchools() } returns flow {}
        every { ucGetAllSat() } returns flow {}
        every { ucFilterSchoolByName(any(),any()) } returns listOf()
        every { ucGetSatByDbn(any(), any()) } returns null

        viewModel = MainPageViewModel(
            ucGetAllSchools,
            ucGetAllSat,
            ucFilterSchoolByName,
            ucGetSatByDbn,
            ioDispatcher
        )
    }

    @Test
    fun `IF empty school list THEN school data is ApiResponse Success with empty list`() {
        every { ucGetAllSchools() } returns flow { emit(ApiResponse.Success(listOf())) }

        viewModel.initializeSchoolData()

        verify { ucGetAllSchools() }
        assertTrue( viewModel.schoolsDataFiltered is ApiResponse.Success )
        assertTrue( (viewModel.schoolsDataFiltered as ApiResponse.Success).body.isEmpty() )
    }

    @Test
    fun `IF non-empty school list THEN usecase list matches school data list`() {
        every { ucGetAllSchools() } returns flow { emit(ApiResponse.Success(listOf(
            SchoolItem.getEmpty().copy(dbn = "a1b2c3"),
            SchoolItem.getEmpty().copy(schoolName = "test1")
        ))) }

        viewModel.initializeSchoolData()

        verify { ucGetAllSchools() }
        assertTrue( viewModel.schoolsDataFiltered is ApiResponse.Success )

        val schoolList = (viewModel.schoolsDataFiltered as ApiResponse.Success).body
        assertTrue( schoolList.isNotEmpty() )
        assertTrue(schoolList.first().dbn == "a1b2c3")
        assertTrue(schoolList[1].schoolName == "test1")
        assertFalse(schoolList.first().schoolName == "test1")
    }

    @Test
    fun `IF search query changed list THEN filter applies`() {
        every { ucGetAllSchools() } returns flow { emit(ApiResponse.Success(listOf(
            SchoolItem.getEmpty().copy(schoolName = "test1"),
            SchoolItem.getEmpty().copy(schoolName = "test2"),
            SchoolItem.getEmpty().copy(schoolName = "tast3"),
            SchoolItem.getEmpty().copy(schoolName = "test4"),
            SchoolItem.getEmpty().copy(schoolName = "best5")
        ))) }

        viewModel.initializeSchoolData()

        var schoolList = (viewModel.schoolsDataFiltered as ApiResponse.Success).body.toList()
        assertTrue( schoolList.count() == 5 )
        assertTrue( schoolList.last().schoolName == "best5" )

        // Due to the instant task executor rule, the delay needs to be tested in the UI.
        viewModel.searchSchoolName("te", immediate = true)

        schoolList = (viewModel.schoolsDataFiltered as ApiResponse.Success).body.toList()
        assertTrue( schoolList.count() == 3 )
        assertTrue( schoolList.last().schoolName == "test4" )
    }
}