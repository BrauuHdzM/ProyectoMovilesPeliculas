package com.example.proyectopeliculas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Perfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val usuario = intent.getStringExtra("usuario")
        val idUsuario = intent.getStringExtra("idUsuario").toString()
        val usuarioTextView = findViewById<TextView>(R.id.txtUser)
        usuarioTextView.text = usuario

        val btnIrActividad = findViewById<Button>(R.id.button)
        btnIrActividad.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation)
        bottomNavigationView.menu.findItem(R.id.action_noticias).isChecked = false
        bottomNavigationView.menu.findItem(R.id.action_inicio).isChecked = false
        bottomNavigationView.menu.findItem(R.id.action_perfil).isChecked = true
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_inicio -> {
                    val intent = Intent(this, PantallaPrincipal::class.java)
                    intent.putExtra("usuario", usuario)
                    intent.putExtra("idUsuario", idUsuario)
                    startActivity(intent)
                    true

                }
                R.id.action_noticias -> {

                    val intent = Intent(this, Noticias::class.java)
                    intent.putExtra("usuario", usuario)
                    intent.putExtra("idUsuario", idUsuario)
                    startActivity(intent)

                    true
                }

                else -> {false}
            }
        }
    }
}