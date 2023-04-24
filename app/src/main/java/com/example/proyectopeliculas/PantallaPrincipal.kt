package com.example.proyectopeliculas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class PantallaPrincipal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_principal)

        val usuario = intent.getStringExtra("usuario")
        val usuarioTextView = findViewById<TextView>(R.id.usuarioPP)
        usuarioTextView.text = usuario
    }
}