package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface LiveDataWrapper {
    fun update(value: UiState)

    fun liveData(): LiveData<UiState>

    class Base : LiveDataWrapper {
        private val liveData: MutableLiveData<UiState> = MutableLiveData()
        override fun update(value: UiState) {
            liveData.value = value
        }

        override fun liveData(): LiveData<UiState> {
            return liveData
        }
    }
}

interface Repository {
    suspend fun load()
    class Base : Repository {
        override suspend fun load() {
            delay(3000)
        }

    }
}

interface UiState {
    fun apply(textView: TextView, button: Button, progressBar: ProgressBar)
    object ShowProgress : UiState {
        override fun apply(textView: TextView, button: Button, progressBar: ProgressBar) {
            textView.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
            button.isEnabled = false
        }
    }
    object ShowData : UiState {
        override fun apply(textView: TextView, button: Button, progressBar: ProgressBar) {
            textView.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
            button.isEnabled = true
        }
    }
}

class MainViewModel(
    val liveDataWrapper: LiveDataWrapper,
    private val repository: Repository
) : ViewModel() {

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun load() {
        liveDataWrapper.update(UiState.ShowProgress)
        viewModelScope.launch {
            repository.load()
            liveDataWrapper.update(UiState.ShowData)
        }
    }

}


class MyApp : Application(){
    val mainViewModel: MainViewModel = MainViewModel(LiveDataWrapper.Base(), Repository.Base())
}
