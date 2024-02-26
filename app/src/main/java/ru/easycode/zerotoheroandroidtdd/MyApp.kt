package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.room.Room
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.core.ViewModelFactory

class MyApp: Application(), ProvideViewModel {

    private val database by lazy {
        Room.databaseBuilder(applicationContext, ItemsDataBase::class.java, "items").fallbackToDestructiveMigration().build()
    }

    private lateinit var viewModelFactory: ViewModelFactory
    private val clear: ClearViewModel = object : ClearViewModel {
        override fun clear(clasz: Class<out ViewModel>) {
            viewModelFactory.clear(clasz)
        }

    }
    override fun onCreate() {
        super.onCreate()
        val provideViewModel = ProvideViewModel.Base(clear, database.itemsDao())
        viewModelFactory = ViewModelFactory.Base(provideViewModel)
    }

    override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T = viewModelFactory.viewModel(viewModelClass)
}