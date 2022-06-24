package org.d3if4003.jadwalbolaapp.model

data class AllFixturesResponse(
    val response: List<FixturesResponse>? = null
)

data class FixturesResponse(
    val fixture: Fixture? = null,
    val teams: Teams? = null
)

data class Fixture(
    val id: Int? = null,
    val date: String? = null
)

data class Teams(
    val home: Home? = null,
    val away: Away? = null
)

data class Home(
    val id: Int? = null,
    val name: String? = null,
    val logo: String? = null
)

data class Away(
    val id: Int? = null,
    val name: String? = null,
    val logo: String? = null
)
