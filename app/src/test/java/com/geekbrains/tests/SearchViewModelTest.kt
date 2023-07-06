package com.geekbrains.tests

import android.os.Build
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.model.SearchResponse
import com.geekbrains.tests.presenter.RepositoryContract
import com.geekbrains.tests.repository.FakeGitHubRepository
import com.geekbrains.tests.viewmodels.ScreenState
import com.geekbrains.tests.viewmodels.SearchViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class SearchViewModelTest {
    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var searchViewModel: SearchViewModel

    @Mock
    private lateinit var repository: RepositoryContract

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        searchViewModel = SearchViewModel(repository)
    }

    @Test
    fun coroutines_TestReturnValueIsNotNull() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<ScreenState> {}
            val liveData = searchViewModel.subscribeToLiveData()
            `when`(repository.searchGithubAsync(SEARCH_QUERY)).thenReturn(
                SearchResponse(1, listOf())
            )
            try {
                liveData.observeForever(observer)
                searchViewModel.searchGitHub(SEARCH_QUERY)
                Assert.assertNotNull(liveData.value)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun coroutines_TestReturnValueIsError() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<ScreenState> {}
            val liveData = searchViewModel.subscribeToLiveData()
            `when`(repository.searchGithubAsync(SEARCH_QUERY)).thenReturn(
                SearchResponse(null, listOf())
            )
            try {
                liveData.observeForever(observer)
                searchViewModel.searchGitHub(SEARCH_QUERY)
                val value: ScreenState.Error = liveData.value as ScreenState.Error
                Assert.assertEquals(value.error.message, ERROR_TEXT)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun coroutines_TestException() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<ScreenState> {}
            val liveData = searchViewModel.subscribeToLiveData()
            try {
                liveData.observeForever(observer)
                searchViewModel.searchGitHub(SEARCH_QUERY)
                val value: ScreenState.Error = liveData.value as ScreenState.Error
                Assert.assertEquals(value.error.message, EXCEPTION_TEXT)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun coroutines_TestReturnValueIsWorking() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<ScreenState> {}
            val liveData = searchViewModel.subscribeToLiveData()
            val searchResponse = SearchResponse(1, listOf())
            `when`(repository.searchGithubAsync(SEARCH_QUERY)).thenReturn(
                searchResponse
            )
            try {
                liveData.observeForever(observer)
                searchViewModel.searchGitHub(SEARCH_QUERY)
                val value: ScreenState.Working = liveData.value as ScreenState.Working
                Assert.assertEquals(value.searchResponse, searchResponse)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    companion object {
        private const val SEARCH_QUERY = "some query"
        private const val ERROR_TEXT = "Search results or total count are null"
        private const val EXCEPTION_TEXT = "Response is null or unsuccessful"
    }
}
