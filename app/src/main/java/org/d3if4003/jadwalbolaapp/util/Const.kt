package org.d3if4003.jadwalbolaapp.util

import java.text.SimpleDateFormat
import java.util.*

class Const {
    companion object{
        const val BASE_URL = "https://api-football-v1.p.rapidapi.com/v3/"
        const val API_KEY = "34595031b1msh1adaee8be48ac38p11192djsna61220b82c0a"
        const val API_HOST = "api-football-v1.p.rapidapi.com"
        const val PREMIER_LEAUGE_ID = "39"
        const val TIMEZONE = "Asia/Jakarta"
        const val SEASON = "2022"
        const val CHANNEL_ID = "10"
        const val NOTIFICATION_ID = 10
        fun parseDate(date: String): String {
            if(date.isNotEmpty()){
                val day = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale("in", "ID")).parse(date)
                return SimpleDateFormat("EEEE, d MMM yyyy", Locale("in", "ID")).format(day)
            }
            return ""
        }
    
        fun getTime(date: String): String{
            if(date.isNotEmpty()){
                val day = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale("in", "ID")).parse(date)
                return SimpleDateFormat("HH:mm", Locale("in", "ID")).format(day)
            }
            return ""
        }
    }
}