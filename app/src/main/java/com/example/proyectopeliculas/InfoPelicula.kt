package com.example.proyectopeliculas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class InfoPelicula : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_pelicula)

        val nombre = intent.getStringExtra("nombre")
        val ano = intent.getIntExtra("ano", 0)
        val actores = intent.getStringExtra("actores")
        val descripcion = intent.getStringExtra("descripcion")

        val nombreTextView = findViewById<TextView>(R.id.nombreTextView)
        val anoTextView = findViewById<TextView>(R.id.anoTextView)
        val actoresTextView = findViewById<TextView>(R.id.actoresTextView)
        val descripcionTextView = findViewById<TextView>(R.id.descripcionTextView)

        nombreTextView.text = nombre
        anoTextView.text = ano.toString()
        actoresTextView.text = actores
        descripcionTextView.text = descripcion
    }
}