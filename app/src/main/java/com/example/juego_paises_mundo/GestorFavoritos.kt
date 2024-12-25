package com.example.juego_paises_mundo

import android.content.Context
import android.content.SharedPreferences

object GestorFavoritos {

    private const val PREFS_NAME = "favoritos"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun anadirFav(context: Context, countryCode: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putBoolean(countryCode, true)
        editor.apply()
    }

    fun eliminarFav(context: Context, countryCode: String) {
        val editor = getSharedPreferences(context).edit()
        editor.remove(countryCode)
        editor.apply()
    }

    fun esFav(context: Context, countryCode: String): Boolean {
        return getSharedPreferences(context).getBoolean(countryCode, false)
    }
}