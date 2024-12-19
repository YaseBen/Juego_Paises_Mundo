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
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_recycleview)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val inputStream = resources.openRawResource(R.raw.paises)
        val reader = InputStreamReader(inputStream)
        val type = object : TypeToken<List<CPais>>() {}.type
        val countries: List<CPais> = Gson().fromJson(reader, type)

        val recyclerView = findViewById<RecyclerView>(R.id.rvPaises)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PaisAdapter(countries)

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