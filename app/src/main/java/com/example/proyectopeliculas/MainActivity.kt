package com.example.proyectopeliculas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private lateinit var usuarioEditText: EditText
    private lateinit var contraseñaEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usuarioEditText = findViewById(R.id.usuario)
        contraseñaEditText = findViewById(R.id.contraseña)

        val iniciarSesionButton: Button = findViewById(R.id.iniciarSesion)
        iniciarSesionButton.setOnClickListener {
            val usuario: String = usuarioEditText.text.toString()
            val contraseña: String = contraseñaEditText.text.toString()

            //Lógica de verificación de usuario y contraseña

            val intent = Intent(this@MainActivity, PantallaPrincipal::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }
    }
}