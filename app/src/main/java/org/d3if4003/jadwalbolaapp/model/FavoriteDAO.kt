package org.d3if4003.jadwalbolaapp.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(favorite: Favorite)
    
    @Delete
    suspend fun remove(favorite: Favorite)
    
    @Query("Select Exists(Select * from favorite_table WHERE id = :fixtureId)")
    fun isFavorite(fixtureId: Int): LiveData<Boolean>
    
    @Query("Select * from favorite_table")
    fun getAllFavorite(): LiveData<List<Favorite>>
}