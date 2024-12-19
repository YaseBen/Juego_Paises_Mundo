package com.example.juego_paises_mundo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PaisAdapter (private val countries: List<CPais>): RecyclerView.Adapter<PaisAdapter.PaisViewHolder>() {

    class PaisViewHolder(View: View) : RecyclerView.ViewHolder(View) {
        val nombre = View.findViewById<TextView>(R.id.tvNombre)
        val capital = View.findViewById<TextView>(R.id.tvCapital)
        val continente = View.findViewById<TextView>(R.id.tvContinente)
        val km2 = View.findViewById<TextView>(R.id.tvKm2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaisViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_card, parent, false)
        return PaisViewHolder(view)
    }

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: PaisViewHolder, position: Int) {
        val country = countries[position]
        holder.nombre.text = country.nombre_es
        holder.capital.text = country.capital_es
        holder.continente.text = country.continente_es
        holder.km2.text = country.km2.toString()
    }
}