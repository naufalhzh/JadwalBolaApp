package org.d3if4003.jadwalbolaapp.retrofit

import org.d3if4003.jadwalbolaapp.model.AllFixturesResponse
import org.d3if4003.jadwalbolaapp.model.HeadToHeadResponse
import org.d3if4003.jadwalbolaapp.model.ListHeadToHeadResponse
import org.d3if4003.jadwalbolaapp.util.Const
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @GET("fixtures")
    fun getAllFixtures(
        @Query("league") leagueId: String,
        @Query("season") season: String,
        @Query("next") count: String,
        @Query("timezone") timezone: String
    ): Call<AllFixturesResponse>
    
    @GET("predictions")
    fun getHeadToHead(
        @Query("fixture") fixtureId: Int
    ): Call<ListHeadToHeadResponse>
}