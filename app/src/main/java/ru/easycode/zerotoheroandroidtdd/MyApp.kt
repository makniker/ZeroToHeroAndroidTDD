package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.room.Room
import ru.easycode.zerotoheroandroidtdd.core.AppDataBase
import ru.easycode.zerotoheroandroidtdd.core.ClearViewModels
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel

class MyApp : Application(), ProvideViewModel {
    private lateinit var factory: ProvideViewModel.Factory
    private val database by lazy {
        Room.databaseBuilder(applicationContext, AppDataBase::class.java, "items")
            .fallbackToDestructiveMigration().build()
    }

    private val clear: ClearViewModels = object : ClearViewModels {

        override fun clear(vararg viewModelClasses: Class<out ViewModel>) {
            for (vm in viewModelClasses) {
                factory.clear(vm)
            }
        }

    }

    override fun onCreate() {
        super.onCreate()
        val provideViewModel =
            ProvideViewModel.Base(database.notesDao(), database.foldersDao(), clear)
        factory = ProvideViewModel.Factory(provideViewModel)
    }

    override fun <T : ViewModel> viewModel(clasz: Class<T>): T = factory.viewModel(clasz)
}