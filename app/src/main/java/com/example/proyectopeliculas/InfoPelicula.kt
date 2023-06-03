package com.example.proyectopeliculas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class InfoPelicula : AppCompatActivity() {
    private lateinit var ratingBar: RatingBar
    private lateinit var comentarioEditText: EditText
    private lateinit var enviarButton: Button
    private lateinit var comentariosAdapter: ComentariosAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_pelicula)

        val idPelicula = intent.getStringExtra("id_pelicula")
        val idUsuario= intent.getStringExtra("id_usuario")
        val nombre = intent.getStringExtra("nombre")
        val ano = intent.getIntExtra("ano", 0)
        val actores = intent.getStringExtra("actores")
        val descripcion = intent.getStringExtra("descripcion")

        val nombreTextView = findViewById<TextView>(R.id.nombreTextView)
        val anoTextView = findViewById<TextView>(R.id.anoTextView)
        val actoresTextView = findViewById<TextView>(R.id.actoresTextView)
        val descripcionTextView = findViewById<TextView>(R.id.descripcionTextView)
        val promedioCalificacionesTextView = findViewById<TextView>(R.id.promedioCalificacionesTextView)

        ratingBar = findViewById(R.id.ratingBar1)
        comentarioEditText = findViewById(R.id.comentarioEditText1)
        enviarButton = findViewById(R.id.enviarButton)

        nombreTextView.text = nombre
        anoTextView.text = ano.toString()
        actoresTextView.text = actores
        descripcionTextView.text = descripcion

        enviarButton.setOnClickListener {
            val calificacion = ratingBar.rating.toDouble()
            val comentario = comentarioEditText.text.toString().trim()

            // Validar que la calificación y el comentario no estén vacíos
            if (calificacion > 0 && comentario.isNotEmpty()) {
                guardarCalificacion(idPelicula, idUsuario, calificacion, comentario)
            } else {
                // Mostrar mensaje de error o realizar alguna acción adicional
            }
        }

        mostrarPromedioCalificaciones(idPelicula)
        obtenerComentariosDesdeFirestore(idPelicula)
    }

    private fun guardarCalificacion(idPelicula: String?, idUsuario: String?, calificacion: Double, comentario: String) {
        val calificacionData = hashMapOf(
            "id_pelicula" to idPelicula,
            "id_usuario" to idUsuario,
            "puntuacion" to calificacion,
            "opinion" to comentario
        )

        db.collection("Calificaciones")
            .add(calificacionData)
            .addOnSuccessListener {
                // Calificación guardada exitosamente
                // Realizar alguna acción adicional si es necesario
            }
            .addOnFailureListener { exception ->
                // Manejar el error al guardar la calificación
            }
    }

    private fun mostrarPromedioCalificaciones(idPelicula: String?) {
        db.collection("Calificaciones")
            .whereEqualTo("id_pelicula", idPelicula)
            .get()
            .addOnSuccessListener { querySnapshot ->
                var totalCalificaciones = 0
                var sumaCalificaciones = 0.0

                for (document in querySnapshot) {
                    val puntuacion = document.getDouble("puntuacion") ?: 0.0
                    totalCalificaciones++
                    sumaCalificaciones += puntuacion
                }

                val promedioCalificaciones = if (totalCalificaciones > 0) {
                    sumaCalificaciones / totalCalificaciones
                } else {
                    0.0
                }

                // Mostrar el promedio de las calificaciones en la interfaz de usuario
                val promedioCalificacionesTextView = findViewById<TextView>(R.id.promedioCalificacionesTextView)
                promedioCalificacionesTextView.text = "El promedio de calificaciones es: ${String.format("%.1f", promedioCalificaciones)}"

            }
            .addOnFailureListener { exception ->
                // Manejar el error al obtener las calificaciones desde Firestore
            }
    }

    private fun obtenerComentariosDesdeFirestore(idPelicula: String?) {
        db.collection("Calificaciones")
            .whereEqualTo("id_pelicula", idPelicula)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val comentariosList: MutableList<Comentario> = mutableListOf()

                for (document in querySnapshot) {
                    val idUsuario = document.getString("id_usuario") ?: ""
                    val puntuacion = document.getDouble("puntuacion") ?: 0.0
                    val opinion = document.getString("opinion") ?: ""

                    val comentario = Comentario(idUsuario, puntuacion, opinion)
                    comentariosList.add(comentario)
                }

                val comentariosRecyclerView = findViewById<RecyclerView>(R.id.comentariosRecyclerView)
                comentariosAdapter = ComentariosAdapter(comentariosList)
                comentariosRecyclerView.layoutManager = LinearLayoutManager(this)
                comentariosRecyclerView.adapter = comentariosAdapter
            }
            .addOnFailureListener { exception ->
                // Manejar la falla en la obtención de los comentarios desde Firestore
            }
    }
}