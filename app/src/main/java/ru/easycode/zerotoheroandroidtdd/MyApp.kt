package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.core.AppDataBase
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel

class MyApp: Application(), ProvideViewModel {
    private val dataBase = AppDataBase.getDatabase(applicationContext)
    private val provideViewModel = ProvideViewModel.Base()
    private val factory = ProvideViewModel.Factory(provideViewModel)

    override fun onCreate() {
        super.onCreate()
    }

    override fun <T : ViewModel> viewModel(clasz: Class<T>): T {
        TODO("Not yet implemented")
    }
}