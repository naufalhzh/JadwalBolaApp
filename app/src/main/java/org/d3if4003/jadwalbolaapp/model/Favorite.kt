package org.d3if4003.jadwalbolaapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_table")
@Parcelize
data class Favorite(
    @PrimaryKey
    val id: Int?,
    val homeTeam: String?,
    val homeLogo: String?,
    val awayTeam: String?,
    val awayLogo: String?,
    val date: String?
) : Parcelable