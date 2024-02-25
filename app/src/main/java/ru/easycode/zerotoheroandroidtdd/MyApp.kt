package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.core.ClearViewModel
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.core.ViewModelFactory

class MyApp : Application(), ProvideViewModel {
    private lateinit var viewModelFactory: ViewModelFactory
    private val clear: ClearViewModel = object : ClearViewModel {
        override fun clear(viewModelClass: Class<out ViewModel>) {
            viewModelFactory.clear(viewModelClass)
        }

    }
    override fun onCreate() {
        super.onCreate()
        val provideViewModel = ProvideViewModel.Base(clear)
        viewModelFactory = ViewModelFactory.Base(provideViewModel)
    }

    override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T = viewModelFactory.viewModel(viewModelClass)
}