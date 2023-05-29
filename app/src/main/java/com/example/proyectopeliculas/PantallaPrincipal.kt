package com.example.proyectopeliculas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class PantallaPrincipal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_principal)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val usuario = intent.getStringExtra("usuario")
        val idUsuario = intent.getStringExtra("idUsuario")
        val usuarioTextView = findViewById<TextView>(R.id.usuarioPP)
        val idUsuarioTextView = findViewById<TextView>(R.id.idUsuario)
        usuarioTextView.text = usuario
        idUsuarioTextView.text = "Con ID: $idUsuario"
    }
}