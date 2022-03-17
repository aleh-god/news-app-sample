package by.godevelopment.newsappsample.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.newsappsample.R
import by.godevelopment.newsappsample.commons.TAG
import by.godevelopment.newsappsample.data.datamodel.NewsModel
import by.godevelopment.newsappsample.domain.FetchNewsUseCase
import by.godevelopment.newsappsample.domain.StringHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchNewsUseCase: FetchNewsUseCase,
    private val stringHelper: StringHelper
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState?> = MutableStateFlow(null)
    val uiState: StateFlow<UiState?> = _uiState

    private val _uiEvent  = MutableSharedFlow<String>(0)
    val uiEvent: SharedFlow<String> = _uiEvent

    init {
        fetchImagesList()
    }

    fun fetchImagesList() {
        viewModelScope.launch {
            fetchNewsUseCase()
                .onStart {
                    Log.i(TAG, "viewModelScope.launch: .onStart")
                    UiState(
                        isFetchingData = true
                    )
                }
                .catch { exception ->
                    Log.i(TAG, "TableViewViewModel: .catch ${exception.message}")
                    _uiState.value = UiState(
                        isFetchingData = false
                    )
                    delay(1000)
                    _uiEvent.emit(stringHelper.getString(R.string.alert_error_loading))
                }
                .collect {
                    Log.i(TAG, "viewModelScope.launch: .collect = ${it.articles.size}")
                    _uiState.value = null
                    _uiState.value = UiState(
                        isFetchingData = false,
                        model = it
                    )
                }
        }
    }

    data class UiState(
        val isFetchingData: Boolean = false,
        val model: NewsModel = NewsModel(
            status = "",
            totalResults = 0,
            articles = listOf()
        )
    )
}