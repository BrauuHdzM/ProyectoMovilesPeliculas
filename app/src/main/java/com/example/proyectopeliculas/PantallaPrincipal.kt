package com.example.proyectopeliculas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class PantallaPrincipal : AppCompatActivity() {
    private lateinit var peliculasRecyclerView: RecyclerView
    private lateinit var peliculaAdapter: PeliculaAdapter
    private val peliculasList: MutableList<Pelicula> = mutableListOf() // Lista de películas a mostrar
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_principal)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.hide()

        val usuario = intent.getStringExtra("usuario")
        val idUsuario = intent.getStringExtra("idUsuario")
        val usuarioTextView = findViewById<TextView>(R.id.usuarioPP)
        usuarioTextView.text = usuario

        peliculasRecyclerView = findViewById(R.id.peliculasRecyclerView)
        peliculaAdapter = PeliculaAdapter(peliculasList)
        peliculasRecyclerView.layoutManager = LinearLayoutManager(this)
        peliculasRecyclerView.adapter = peliculaAdapter

        obtenerPeliculasDesdeFirestore()
    }

    private fun obtenerPeliculasDesdeFirestore() {
        db.collection("Peliculas")
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val nombre = document.getString("nombre") ?: ""
                    val ano = document.getLong("ano")?.toInt() ?: 0
                    val actores = document.getString("actores") ?: ""
                    val descripcion = document.getString("descripcion") ?: ""

                    val pelicula = Pelicula(nombre, ano, actores, descripcion)
                    peliculasList.add(pelicula)
                }

                peliculaAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Manejar la falla en la obtención de los datos desde Firestore
            }
    }




}