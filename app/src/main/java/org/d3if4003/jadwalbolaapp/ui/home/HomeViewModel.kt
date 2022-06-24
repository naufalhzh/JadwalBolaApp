package org.d3if4003.jadwalbolaapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if4003.jadwalbolaapp.model.AllFixturesResponse
import org.d3if4003.jadwalbolaapp.model.FixturesResponse
import org.d3if4003.jadwalbolaapp.retrofit.ApiClient
import org.d3if4003.jadwalbolaapp.util.Const
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val listFixtures = MutableLiveData<List<FixturesResponse>>()
    
    fun getAllFixtures(){
        val apiService = ApiClient().getService()
        isLoading.value = true
        apiService.getAllFixtures(Const.PREMIER_LEAUGE_ID, Const.SEASON, "10", Const.TIMEZONE)
            .enqueue(object: Callback<AllFixturesResponse>{
                override fun onResponse(call: Call<AllFixturesResponse>, response: Response<AllFixturesResponse>) {
                    isLoading.value = false
                    if(response.isSuccessful){
                        response.body()?.response?.let {
                            listFixtures.value = it
                        }
                    } else {
                        errorMessage.value = response.message()
                    }
                }
    
                override fun onFailure(call: Call<AllFixturesResponse>, t: Throwable) {
                    isLoading.value = false
                    errorMessage.value = t.localizedMessage
                }
    
            })
    }
}