package com.example.proyectopeliculas

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import com.squareup.picasso.Picasso

class DetallesNoticiaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_noticia)

        // Obtener el texto completo de la noticia del intent
        val textoCompleto = intent.getStringExtra("textoCompleto")

        val encabezado= intent.getStringExtra("encabezado")
        val imagen = intent.getStringExtra("imagen")
        val imageView = findViewById<ImageView>(R.id.imageViewNoticia)
        Picasso.get().load(imagen).into(imageView)



        // Mostrar el texto completo en la interfaz de usuario
        val textViewCuerpo = findViewById<TextView>(R.id.txtTextoCompleto)
        val textViewEncabezado = findViewById<TextView>(R.id.txtTextoEncabezado)

        textViewCuerpo.text = textoCompleto
        textViewEncabezado.text = encabezado

    }
}
