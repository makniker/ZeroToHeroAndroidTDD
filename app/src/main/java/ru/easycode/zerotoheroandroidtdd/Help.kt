package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.Serializable

interface BundleWrapper {
    interface Mutable: Save, Restore {
        class Base(private val bundle: Bundle): Mutable {
            override fun save(uiState: UiState) {
                bundle.putSerializable("key", uiState)
            }

            @RequiresApi(Build.VERSION_CODES.TIRAMISU)
            override fun restore(): UiState {
                return bundle.getSerializable("key", UiState::class.java)!!
            }
        }
    }
    interface Save {
        fun save(uiState: UiState)
    }
    interface Restore {
        fun restore(): UiState
    }
}

interface LiveDataWrapper {
    fun update(value: UiState)

    fun liveData(): LiveData<UiState>

    fun save(bundleWrapper: BundleWrapper.Save)

    class Base : LiveDataWrapper {
        private val liveData: MutableLiveData<UiState> = SingleLiveEvent()
        override fun update(value: UiState) {
            liveData.value = value
        }

        override fun liveData(): LiveData<UiState> {
            return liveData
        }

        override fun save(bundleWrapper: BundleWrapper.Save) {
            liveData.value?.let { bundleWrapper.save(it) }
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

interface UiState: Serializable {
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

    fun save(bundleWrapper: BundleWrapper.Save) {
        liveDataWrapper.save(bundleWrapper)
    }

    fun restore(bundleWrapper: BundleWrapper.Restore) {
        val state = bundleWrapper.restore()
        liveDataWrapper.update(state)
    }
}


class MyApp : Application() {

    val viewModel = MainViewModel(LiveDataWrapper.Base(), Repository.Base())
}