package org.d3if4003.jadwalbolaapp

import androidx.datastore.dataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if4003.jadwalbolaapp.preferences.UIModeManager

class MainViewModel: ViewModel() {
    val networkAvailable = MutableLiveData<Boolean>()
    val isNightMode = MutableLiveData<Boolean>()
    
    fun setNetworkState(boolean: Boolean){
        networkAvailable.value = boolean
    }
    
    fun setNightMode(boolean: Boolean){
        isNightMode.value = boolean
    }
}