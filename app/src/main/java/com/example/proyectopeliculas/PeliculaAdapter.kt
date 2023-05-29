package com.example.proyectopeliculas

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PeliculaAdapter(private val peliculas: List<Pelicula>) :
    RecyclerView.Adapter<PeliculaAdapter.PeliculaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.fila_pelicula,
            parent,
            false
        )
        val viewHolder = PeliculaViewHolder(itemView)

        itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val pelicula = peliculas[position]

            val intent = Intent(parent.context, InfoPelicula::class.java)
            intent.putExtra("nombre", pelicula.nombre)
            intent.putExtra("ano", pelicula.ano)
            intent.putExtra("actores", pelicula.actores)
            intent.putExtra("descripcion", pelicula.descripcion)
            parent.context.startActivity(intent)
        }

        return viewHolder
    }


    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        val pelicula = peliculas[position]
        holder.bind(pelicula)
    }

    override fun getItemCount(): Int {
        return peliculas.size
    }

    inner class PeliculaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.nombreTextView)
        private val anoTextView: TextView = itemView.findViewById(R.id.anoTextView)
        private val actoresTextView: TextView = itemView.findViewById(R.id.actoresTextView)
        private val descripcionTextView: TextView = itemView.findViewById(R.id.descripcionTextView)

        fun bind(pelicula: Pelicula) {
            nombreTextView.text = pelicula.nombre
            anoTextView.text = pelicula.ano.toString()
            actoresTextView.text = pelicula.actores
            descripcionTextView.text = pelicula.descripcion
        }
    }
}



