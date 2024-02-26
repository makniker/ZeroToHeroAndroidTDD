package ru.easycode.zerotoheroandroidtdd.core

interface ListLiveDataWrapper {
    interface Mutable : Update, Read
    class Base : LiveDataWrapper.Abstract<String>(), Mutable, Add
    interface Read : LiveDataWrapper.Read<String>
    interface Update : LiveDataWrapper.Update<String>

    interface Add : LiveDataWrapper.Add<String>

}