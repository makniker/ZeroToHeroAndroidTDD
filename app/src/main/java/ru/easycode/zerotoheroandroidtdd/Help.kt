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
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
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
    suspend fun load(): SimpleResponse
    class Base(private val service: SimpleService, private val url: String) : Repository {
        override suspend fun load(): SimpleResponse {
            return service.fetch(url)
        }

    }
}

interface SimpleService {
    @GET
    suspend fun fetch(@Url url: String): SimpleResponse
}

data class SimpleResponse(@SerializedName("text") val text: String)

interface UiState: Serializable {
    fun apply(textView: TextView, button: Button, progressBar: ProgressBar)
    object ShowProgress : UiState {

        override fun apply(textView: TextView, button: Button, progressBar: ProgressBar) {
            textView.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
            button.isEnabled = false
        }
    }
    data class ShowData(private val text: String) : UiState {
        override fun apply(textView: TextView, button: Button, progressBar: ProgressBar) {
            textView.visibility = View.VISIBLE
            textView.text = text
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
                liveDataWrapper.update(UiState.ShowData(repository.load().text))
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
    lateinit var viewModel: MainViewModel
    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.google.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: SimpleService = retrofit.create(SimpleService::class.java)
        viewModel = MainViewModel(LiveDataWrapper.Base(), Repository.Base(service, "https://raw.githubusercontent.com/JohnnySC/ZeroToHeroAndroidTDD/task/018-clouddatasource/app/sampleresponse.json"))
    }
}