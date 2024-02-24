package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import ru.easycode.zerotoheroandroidtdd.core.ClearViewModel
import ru.easycode.zerotoheroandroidtdd.core.Navigation
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.core.ViewModelFactory
import ru.easycode.zerotoheroandroidtdd.list.ListLiveDataWrapper

class MyApp : Application() {
    val viewModelFactory = ViewModelFactory.Base(ProvideViewModel.Base(Navigation.Mutable.Base(), ListLiveDataWrapper.All.Base(), ClearViewModel.Base()))
}