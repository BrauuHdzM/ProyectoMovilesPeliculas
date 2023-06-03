package com.example.proyectopeliculas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class InfoPelicula : AppCompatActivity() {
    private lateinit var ratingBar: RatingBar
    private lateinit var comentarioEditText: EditText
    private lateinit var enviarButton: Button
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
    }

    private fun guardarCalificacion(idPelicula: String?, idUsuario:String?, calificacion: Double, comentario: String) {
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
}