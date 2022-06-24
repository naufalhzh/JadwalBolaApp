package org.d3if4003.jadwalbolaapp.model
data class ListHeadToHeadResponse(
    val response: List<HeadToHeadResponse>? = null
)

data class HeadToHeadResponse(
    val h2h: List<HeadToHead>? = null
)

data class HeadToHead(
    val teams: Teams? = null,
    val goals: Goals? = null
)

data class Goals(
    val home: Int? = null,
    val away: Int? = null
)