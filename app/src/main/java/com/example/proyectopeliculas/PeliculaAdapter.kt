package com.example.proyectopeliculas

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PeliculaAdapter(
    private val peliculas: List<Pelicula>,
    private val calificaciones: List<Calificaciones>,
    private val peliculasIds: List<String>,
    private val idUsuario: String
) :
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
            val idPelicula = peliculasIds[position]
            val calificacion = if (position < calificaciones.size) calificaciones[position] else null

            val intent = Intent(parent.context, InfoPelicula::class.java)
            intent.putExtra("id_pelicula", idPelicula)
            intent.putExtra("id_usuario", idUsuario)
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
        val calificacion = if (position < calificaciones.size) calificaciones[position] else null
        holder.bind(pelicula, calificacion)
    }

    override fun getItemCount(): Int {
        return peliculas.size
    }

    inner class PeliculaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.nombreTextView)
        private val anoTextView: TextView = itemView.findViewById(R.id.anoTextView)
        private val actoresTextView: TextView = itemView.findViewById(R.id.actoresTextView)
        private val descripcionTextView: TextView = itemView.findViewById(R.id.descripcionTextView)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)

        fun bind(pelicula: Pelicula, calificacion: Calificaciones?) {
            nombreTextView.text = pelicula.nombre
            anoTextView.text = pelicula.ano.toString()
            actoresTextView.text = pelicula.actores
            descripcionTextView.text = pelicula.descripcion

            if (calificacion != null) {
                ratingBar.rating = calificacion.puntuacion.toFloat()
            } else {
                ratingBar.rating = 0f
            }
        }
    }
}



