package org.d3if4003.jadwalbolaapp.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.d3if4003.jadwalbolaapp.model.Favorite
import org.d3if4003.jadwalbolaapp.model.FavoriteDAO
import org.d3if4003.jadwalbolaapp.model.JadwalBolaDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    val listFavorite: LiveData<List<Favorite>>
    
    val dao: FavoriteDAO
    
    init {
        dao = JadwalBolaDatabase.getDatabase(application).getFavoriteDao()
        listFavorite = dao.getAllFavorite()
    }
}