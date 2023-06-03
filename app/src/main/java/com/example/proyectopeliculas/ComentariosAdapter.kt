package com.example.proyectopeliculas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ComentariosAdapter(private val comentariosList: List<Comentario>) :
    RecyclerView.Adapter<ComentariosAdapter.ComentarioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.fila_comentario,
            parent,
            false
        )
        return ComentarioViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ComentarioViewHolder, position: Int) {
        val comentario = comentariosList[position]
        holder.bind(comentario)
    }

    override fun getItemCount(): Int {
        return comentariosList.size
    }

    inner class ComentarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val usuarioTextView: TextView = itemView.findViewById(R.id.usuarioTextViewFC)
        private val puntuacionTextView: TextView = itemView.findViewById(R.id.puntuacionTextViewFC)
        private val opinionTextView: TextView = itemView.findViewById(R.id.opinionTextViewFC)

        fun bind(comentario: Comentario) {
            usuarioTextView.text = comentario.idUsuario
            puntuacionTextView.text = comentario.puntuacion.toString()
            opinionTextView.text = comentario.opinion
        }
    }
}
