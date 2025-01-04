package com.example.juego_paises_mundo

import java.io.Serializable

data class CPais(
    val name_es: String,
    val continent_es: String,
    val capital_es: String,
    val km2: Int,
    val code_3: String,
) : Serializable