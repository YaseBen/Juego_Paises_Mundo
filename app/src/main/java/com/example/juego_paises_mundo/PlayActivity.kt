package com.example.juego_paises_mundo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PlayActivity : AppCompatActivity(){

    lateinit var paisesList: List<CPais>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        supportActionBar?.hide()

        setContentView(R.layout.activity_play)

        paisesList = (intent.getSerializableExtra("paisesList") as? ArrayList<CPais>)!!

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val random = paisesList.random()
        val randomPais = random.name_es
        val randomCapital = random.capital_es

        val pPais = findViewById<TextView>(R.id.tvPregunta)
        pPais.text = randomPais

        val button1 = findViewById<Button>(R.id.bO1)
        val button2 = findViewById<Button>(R.id.bO2)
        val button3 = findViewById<Button>(R.id.bO3)
        val button4 = findViewById<Button>(R.id.bO4)
        val buttonList = listOf(button1,button2,button3,button4)

        val randomB = buttonList.random()

        randomB.text = randomCapital

        for (i in 1..3){

            if (buttonList[i]==randomB){

            }else{
                val button = buttonList[i]
                val random = paisesList.random()
                val randomCapital = random.capital_es

                button.text = randomCapital
            }
        }
    }

}