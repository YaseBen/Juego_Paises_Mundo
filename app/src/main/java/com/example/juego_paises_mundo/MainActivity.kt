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

    private lateinit var paisesList: List<CPais>
    private lateinit var paisesFiltrados: List<CPais>
    private lateinit var paisAdapter: PaisAdapter

    private var filtroFavoritos = false
    private val continentesSeleccionados = mutableSetOf<String>()
    private var filtroCapitalesAsc = false
    private var filtroCapitalesDesc = false

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

        paisesList = cargaPaises()
        paisesFiltrados = paisesList

        paisAdapter = PaisAdapter(paisesFiltrados, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = paisAdapter

    }

    private fun cargaPaises(): List<CPais> {

        val jsonInputStream = resources.openRawResource(R.raw.paises)
        val jsonString = jsonInputStream.bufferedReader().use { it.readText() }

        val gson = Gson()
        val jsonObject = gson.fromJson(jsonString, JsonObject::class.java)

        val jsonArray = jsonObject.getAsJsonArray("countries")

        return jsonArray.map { elementoJson ->
            gson.fromJson(elementoJson, CPais::class.java)
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
                intent.putExtra("paisesFiltrados",ArrayList(paisesFiltrados))
                startActivity(intent)
                finish()

                return true
            }

            R.id.Favoritos->{

                paisesList = cargaPaises()

                item.isChecked = !item.isChecked

                paisesFiltrados = if (item.isChecked) {
                    paisesList.filter { GestorFavoritos.esFav(this, it.code_3) }
                } else {
                    paisesList
                }

                paisAdapter.updateList(paisesFiltrados)
                true

            }

            R.id.P_Asc->{

                item.isChecked = !item.isChecked

                paisesFiltrados = if(item.isChecked){
                    paisesFiltrados.sortedBy{it.name_es}
                } else {
                    cargaPaises()
                }

                paisAdapter.updateList(paisesFiltrados)

                true
            }

            R.id.P_Desc->{

                item.isChecked = !item.isChecked

                paisesFiltrados = if (item.isChecked){
                    paisesFiltrados.sortedByDescending { it.name_es }
                } else {
                    cargaPaises()
                }

                paisAdapter.updateList(paisesFiltrados)

                true
            }

            R.id.C_Asc->{

                item.isChecked = !item.isChecked

                paisesFiltrados = if (item.isChecked){
                    paisesFiltrados.sortedBy { it.capital_es }
                } else {
                    cargaPaises()
                }

                paisAdapter.updateList(paisesFiltrados)

                true
            }

            R.id.C_Desc->{

                item.isChecked = !item.isChecked

                paisesFiltrados = if (item.isChecked){
                    paisesFiltrados.sortedByDescending { it.capital_es }
                } else {
                    cargaPaises()
                }

                true
            }

            R.id.cAfrica, R.id.cAsia, R.id.cEuropa, R.id.cAdN, R.id.cAdS, R.id.cOceania -> {

                item.isChecked = !item.isChecked

                if (item.isChecked) {

                    filtrarPorContinente(item.title.toString())

                } else {

                    paisesFiltrados = cargaPaises()

                }

                paisAdapter.updateList(paisesFiltrados)

                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun filtrarPorContinente(toString: String) {

        paisesList = cargaPaises()
        paisesFiltrados = paisesList.filter { it.continent_es == toString }

    }
}