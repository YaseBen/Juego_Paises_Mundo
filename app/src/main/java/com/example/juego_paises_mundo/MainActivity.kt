package com.example.juego_paises_mundo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
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

    private var filtroContinente: String? = null
    private var filtroOrdenPaisAscendente: Boolean = false
    private var filtroOrdenPaisDescendente: Boolean = false
    private var filtroOrdenCapitalAscendente: Boolean = false
    private var filtroOrdenCapitalDescendente: Boolean = false
    private var soloFavoritos: Boolean = false

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

        paisAdapter = PaisAdapter(paisesFiltrados, this){ pais ->

            val wikipediaUrl = "https://es.wikipedia.org/wiki/${pais.name_es.replace(" ", "_")}"

            val uri = Uri.parse(wikipediaUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)

        }
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

                item.isChecked = !item.isChecked
                soloFavoritos = item.isChecked
                aplicarFiltros()
                true

            }

            R.id.P_Asc->{

                item.isChecked = !item.isChecked
                filtroOrdenPaisAscendente = item.isChecked
                filtroOrdenPaisDescendente = false
                aplicarFiltros()
                true
            }

            R.id.P_Desc->{

                item.isChecked = !item.isChecked
                filtroOrdenPaisDescendente = item.isChecked
                filtroOrdenPaisAscendente = false
                aplicarFiltros()
                true
            }

            R.id.C_Asc->{

                item.isChecked = !item.isChecked
                filtroOrdenCapitalAscendente = item.isChecked
                filtroOrdenCapitalDescendente = false
                aplicarFiltros()
                true
            }

            R.id.C_Desc->{

                item.isChecked = !item.isChecked
                filtroOrdenCapitalDescendente = item.isChecked
                filtroOrdenCapitalAscendente = false
                aplicarFiltros()
                true
            }

            R.id.cAfrica, R.id.cAsia, R.id.cEuropa, R.id.cAdN, R.id.cAdS, R.id.cOceania -> {

                item.isChecked = !item.isChecked
                filtroContinente = if (item.isChecked) {
                    item.title.toString()
                } else {
                    null
                }
                aplicarFiltros()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun aplicarFiltros() {
        paisesFiltrados = paisesList

        filtroContinente?.let { continente ->
            paisesFiltrados = paisesFiltrados.filter { it.continent_es == continente }
        }

        if (soloFavoritos) {
            paisesFiltrados = paisesFiltrados.filter { GestorFavoritos.esFav(this, it.code_3) }
        }

        when {
            filtroOrdenPaisAscendente -> paisesFiltrados = paisesFiltrados.sortedBy { it.name_es }
            filtroOrdenPaisDescendente -> paisesFiltrados = paisesFiltrados.sortedByDescending { it.name_es }
        }

        when {
            filtroOrdenCapitalAscendente -> paisesFiltrados = paisesFiltrados.sortedBy { it.capital_es }
            filtroOrdenCapitalDescendente -> paisesFiltrados = paisesFiltrados.sortedByDescending { it.capital_es }
        }

        paisAdapter.updateList(paisesFiltrados)
    }
}