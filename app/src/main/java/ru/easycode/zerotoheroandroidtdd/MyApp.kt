package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.room.Room
import ru.easycode.zerotoheroandroidtdd.data.ItemsDataBase
import ru.easycode.zerotoheroandroidtdd.presentation.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.presentation.ViewModelFactory
import ru.easycode.zerotoheroandroidtdd.presentation.core.ClearViewModel

class MyApp: Application(), ProvideViewModel {

    private val database by lazy {
        Room.databaseBuilder(applicationContext, ItemsDataBase::class.java, "items").fallbackToDestructiveMigration().build()
    }

    private lateinit var viewModelFactory: ViewModelFactory
    private val clear: ClearViewModel = object : ClearViewModel {
        override fun clearViewModel(clasz: Class<out ViewModel>) {
            viewModelFactory.clearViewModel(clasz)
        }

    }
    override fun onCreate() {
        super.onCreate()
        val provideViewModel = ProvideViewModel.Base(clear, database.itemsDao())
        viewModelFactory = ViewModelFactory.Base(provideViewModel)
    }

    override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T = viewModelFactory.viewModel(viewModelClass)
}