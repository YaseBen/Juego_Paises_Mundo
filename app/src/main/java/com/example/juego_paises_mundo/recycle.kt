package com.example.juego_paises_mundo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class recycle: AppCompatActivity() {
    lateinit var myRecyclerView : RecyclerView
    lateinit var mAdapter: PaisAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        myRecyclerView = findViewById(R.id.rvPaises)
        myRecyclerView.setHasFixedSize(true)
        myRecyclerView.layoutManager = LinearLayoutManager(this)

        mAdapter = PaisAdapter(getPaises(), this)
        myRecyclerView.adapter = mAdapter
    }

    private fun getPaises(): List<CPais> {
        val paisos = mutableListOf<CPais>() // Lista de objetos cPais

        val inputStream = assets.open("paisos.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val jsonString = String(buffer, Charsets.UTF_8)

        val jsonArray = org.json.JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            val pais = jsonArray.getJSONObject(i)

            val nombre = pais.getString("name_es")
            val capital = pais.getString("capital_es")
            val continente = pais.getString("continent_es")
            val km2 = pais.getInt("km2")
            val code_3 = pais.getString("code_3")

            paisos.add(CPais(nombre, continente, capital, km2, code_3))
        }

        return paisos
    }
}