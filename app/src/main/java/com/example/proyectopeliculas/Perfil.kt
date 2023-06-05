package com.example.proyectopeliculas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class Perfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)



        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation)
        bottomNavigationView.menu.findItem(R.id.action_noticias).isChecked = false
        bottomNavigationView.menu.findItem(R.id.action_inicio).isChecked = false
        bottomNavigationView.menu.findItem(R.id.action_perfil).isChecked = true
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_inicio -> {
                    val intent = Intent(this, PantallaPrincipal::class.java)
                    startActivity(intent)
                    true

                }
                R.id.action_noticias -> {

                    val intent = Intent(this, Noticias::class.java)
                    startActivity(intent)

                    true
                }

                else -> {false}
            }
        }
    }
}