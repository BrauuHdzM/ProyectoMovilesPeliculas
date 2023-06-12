package com.example.proyectopeliculas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Noticias : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = NoticiaAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter= adapter


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation)
        bottomNavigationView.menu.findItem(R.id.action_noticias).isChecked = true
        bottomNavigationView.menu.findItem(R.id.action_inicio).isChecked = false
        bottomNavigationView.menu.findItem(R.id.action_perfil).isChecked = false

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_perfil -> {
                    val intent = Intent(this, Perfil::class.java)

                    startActivity(intent)


                    true

                }
                R.id.action_inicio -> {

                    val intent = Intent(this, PantallaPrincipal::class.java)
                    startActivity(intent)

                    true
                }

                else -> {false}
            }
        }


    }
}