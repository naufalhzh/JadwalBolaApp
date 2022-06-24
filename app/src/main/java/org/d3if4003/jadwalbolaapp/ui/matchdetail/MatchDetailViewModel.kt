package org.d3if4003.jadwalbolaapp.ui.matchdetail

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if4003.jadwalbolaapp.model.*
import org.d3if4003.jadwalbolaapp.retrofit.ApiClient
import org.d3if4003.jadwalbolaapp.util.Const
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MatchDetailViewModel(application: Application): AndroidViewModel(application) {
    
    val listHeadToHead = MutableLiveData<List<HeadToHead>>()
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    
    val dao: FavoriteDAO
    
    init {
        dao = JadwalBolaDatabase.getDatabase(application).getFavoriteDao()
    }
    
    fun getHeadToHead(fixtureId: Int) {
        val apiService = ApiClient().getService()
        isLoading.value = true
        apiService.getHeadToHead(fixtureId)
            .enqueue(object: Callback<ListHeadToHeadResponse> {
                override fun onResponse(call: Call<ListHeadToHeadResponse>, response: Response<ListHeadToHeadResponse>) {
                    isLoading.value = false
                    if(response.isSuccessful){
                        response.body()?.response?.get(0)?.h2h.let {
                            listHeadToHead.value = it
                        }
                    } else {
                        errorMessage.value = response.message()
                    }
                }
            
                override fun onFailure(call: Call<ListHeadToHeadResponse>, t: Throwable) {
                    isLoading.value = false
                    errorMessage.value = t.localizedMessage
                }
            
            })
    }
    
    fun getFavorite(fixtureId: Int): LiveData<Boolean>{
        return dao.isFavorite(fixtureId)
    }
    
    fun addToFavorite(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO){
        dao.addToFavorite(favorite)
    }
    
    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO){
        dao.remove(favorite)
    }
}