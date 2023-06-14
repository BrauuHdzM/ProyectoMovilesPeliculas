package com.example.proyectopeliculas

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import java.io.InputStream
import com.squareup.picasso.Picasso


class NoticiaAdapter : RecyclerView.Adapter <NoticiaAdapter.ManejadorVista> (){

    class ManejadorVista(elementoVista: View) :
        RecyclerView.ViewHolder(elementoVista)
    {
        var imagenNoticia: ImageView
        var encabezadoNoticia: TextView
        var resumenNoticia: TextView
        init {
            imagenNoticia=elementoVista.findViewById(R.id.imagenNoticia)
            encabezadoNoticia=elementoVista.findViewById(R.id.txtTituloN)
            resumenNoticia=elementoVista.findViewById(R.id.txtDescipcionN)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManejadorVista {
        var vista= LayoutInflater.from(parent.context).inflate(R.layout.card_item_layout_noticia,parent,false)
        return ManejadorVista(vista)
    }

    override fun getItemCount(): Int {
     return 4
    }
    override fun onBindViewHolder(holder: ManejadorVista, position: Int) {
        // Leer el contenido del archivo JSON y obtener los datos necesarios
        val contenidoArchivo = leerArchivoJSON(holder.itemView.context)  // Pasar el contexto de la vista para acceder a los recursos

        // Verificar si la posición está dentro del rango del contenido del archivo
        if (position < contenidoArchivo.length()) {
            val noticia = contenidoArchivo.getJSONObject(position)  // Obtener el objeto de noticia en la posición actual

            // Obtener los valores de las propiedades en el objeto de noticia
            val imagen = noticia.getString("imagen")
            val encabezado = noticia.getString("encabezado")
            val resumen = noticia.getString("resumen")

            Picasso.get().load(imagen).into(holder.imagenNoticia)
            holder.encabezadoNoticia.text = encabezado
            holder.resumenNoticia.text = resumen

            // Agregar el listener de clic al titular de la noticia
            holder.itemView.setOnClickListener {
                // Obtener la noticia seleccionada
                val noticiaSeleccionada = contenidoArchivo.getJSONObject(holder.adapterPosition)

                // Obtener los detalles de la noticia (por ejemplo, el texto completo)
                val textoCompleto = noticiaSeleccionada.getString("cuerpoNoticia")
                val encabezadoN = noticiaSeleccionada.getString("encabezado")
                val video= noticiaSeleccionada.getString("imagen")


                // Crear un intent para abrir la actividad de detalles de la noticia
                val intent = Intent(holder.itemView.context, DetallesNoticiaActivity::class.java)
                intent.putExtra("encabezado", encabezadoN)
                intent.putExtra("textoCompleto", textoCompleto)
                intent.putExtra("imagen", video)
                holder.itemView.context.startActivity(intent)
            }
        }
    }



    // Función para leer el contenido del archivo JSON y devolverlo como un objeto JSONArray
    private fun leerArchivoJSON(context: Context): JSONArray {
        val inputStream: InputStream = context.resources.openRawResource(R.raw.noticias)  // Reemplaza "datos" con el nombre real de tu archivo JSON en res/raw
        val json = inputStream.bufferedReader().use { it.readText() }  // Leer el contenido del archivo como un String

        return JSONArray(json)
    }
}




