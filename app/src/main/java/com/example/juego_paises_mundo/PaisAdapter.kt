package com.example.juego_paises_mundo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class PaisAdapter(
    private val paises: List<CPais>,
    private val context: Context
): RecyclerView.Adapter<PaisAdapter.PaisViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaisAdapter.PaisViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_card,parent,false)

        return PaisViewHolder(view)

    }

    override fun onBindViewHolder(holder: PaisAdapter.PaisViewHolder, position: Int) {

        val pais = paises[position]
        holder.bind(pais)

    }

    override fun getItemCount(): Int = paises.size

    inner class PaisViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvCapital: TextView = itemView.findViewById(R.id.tvCapital)
        val tvContinente: TextView = itemView.findViewById(R.id.tvContinente)
        val tvKm2:TextView = itemView.findViewById(R.id.tvKm2)
        val cardView: CardView = itemView.findViewById(R.id.CardView)
        val fav: ImageView = itemView.findViewById(R.id.ivStar)

        fun bind(pais:CPais){

            tvNombre.text = pais.name_es
            tvCapital.text = pais.capital_es
            tvContinente.text = pais.continent_es
            tvKm2.text = pais.km2.toString()

            val color = when (pais.continent_es) {
                "África" -> itemView.context.getColor(R.color.africa_color)
                "Asia" -> itemView.context.getColor(R.color.asia_color)
                "Europa" -> itemView.context.getColor(R.color.europe_color)
                "América del Norte" -> itemView.context.getColor(R.color.america_color)
                "América del Sur" -> itemView.context.getColor(R.color.americasur_color)
                "Oceanía" -> itemView.context.getColor(R.color.oceania_color)
                else -> itemView.context.getColor(R.color.default_color)
            }

            cardView.setCardBackgroundColor(color)

            val isFav = GestorFavoritos.esFav(context,pais.code_3)

            fav.setImageResource(
                if (isFav) R.drawable.baseline_star_24 else R.drawable.baseline_star_border_24
            )

            fav.setOnClickListener{

                if (isFav){
                    GestorFavoritos.eliminarFav(context,pais.code_3)
                    fav.setImageResource(R.drawable.baseline_star_border_24)
                }else{
                    GestorFavoritos.anadirFav(context, pais.code_3)
                    fav.setImageResource(R.drawable.baseline_star_24)
                }

            }
        }
    }

}