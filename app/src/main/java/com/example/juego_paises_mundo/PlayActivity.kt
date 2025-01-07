package com.example.juego_paises_mundo

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

        paisesList = (intent.getSerializableExtra("paisesFiltrados") as? ArrayList<CPais>)!!

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        generadorPregunta()

    }

    private fun generadorPregunta() {
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

        for (i in 0..3){

            if (buttonList[i]==randomB){

            }else{
                val button = buttonList[i]
                val random = paisesList.random()
                val randomCapital = random.capital_es

                button.text = randomCapital
            }
        }

        for (button in buttonList) {
            button.setOnClickListener {
                if (button.text == randomCapital) {
                    button.setBackgroundColor(Color.parseColor("#186a3b"))
                } else {
                    button.setBackgroundColor(Color.parseColor("#a93226"))
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }

                buttonList.forEach { it.isEnabled = false }

                Handler(Looper.getMainLooper()).postDelayed({
                    reseteoBotones(buttonList)
                    generadorPregunta()
                }, 1000)
                
            }
        }
    }

    private fun reseteoBotones(buttonList: List<Button>) {
        for (button in buttonList){
            button.isEnabled = true
            button.setBackgroundColor(Color.parseColor("#FF6500"))
            button.text = ""
        }
    }

}