package com.example.juego_paises_mundo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject

class MainActivity : AppCompatActivity() {

    lateinit var GestorFavoritos: GestorFavoritos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView: RecyclerView = findViewById(R.id.rvPaises)

        val paisesList = cargaPaises()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PaisAdapter(paisesList,this)

    }

    private fun cargaPaises(): List<CPais> {

        val jsonInputStream = resources.openRawResource(R.raw.paises)
        val jsonString = jsonInputStream.bufferedReader().use { it.readText() }

        val gson = Gson()
        val jsonObject = gson.fromJson(jsonString, JsonObject::class.java)

        val jsonArray = jsonObject.getAsJsonArray("countries")

        return jsonArray.map { elementoJson ->
            gson.fromJson(elementoJson,CPais::class.java)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.custom_menu,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.Play->{

                val intent = Intent(this,PlayActivity::class.java)
                startActivity(intent)
                finish()

                return true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}