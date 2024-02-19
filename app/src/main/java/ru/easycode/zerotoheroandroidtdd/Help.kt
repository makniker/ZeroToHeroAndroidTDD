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
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface BundleWrapper {
    interface Mutable: Save, Restore {
        class Base(): Mutable {
            private lateinit var uiState: UiState
            override fun save(uiState: UiState) {
                this.uiState = uiState
            }

            override fun restore(): UiState {
                return uiState
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
        private val liveData: MutableLiveData<UiState> = MutableLiveData()
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

    fun save(bundleWrapper: BundleWrapper.Save) {
        liveDataWrapper.save(bundleWrapper)
    }

    fun restore(bundleWrapper: BundleWrapper.Restore) {
        liveDataWrapper.update(bundleWrapper.restore())
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedState = (application as MyApp).bundle
                val model = MainViewModel(LiveDataWrapper.Base(), Repository.Base())
                if (savedState != null) {
                    model.restore(savedState)
                }
                return model as T
            }
        }
    }

}


class MyApp : Application(){
    var bundle: BundleWrapper.Mutable? = null
}