package com.example.proyectopeliculas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity() {

    private lateinit var usuarioEditText: EditText
    private lateinit var contraseñaEditText: EditText
    private val db= FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayShowTitleEnabled(false)


        setContentView(R.layout.activity_main)

        usuarioEditText = findViewById(R.id.usuario)
        contraseñaEditText = findViewById(R.id.contraseña)

        val iniciarSesionButton: Button = findViewById(R.id.iniciarSesion)
        val crearCuentaTextView = findViewById<TextView>(R.id.crearCuenta)
        val recuperarContrasena = findViewById<TextView>(R.id.olvideContraseña)

        iniciarSesionButton.setOnClickListener {
            val usuario: String = usuarioEditText.text.toString()
            val contraseña: String = contraseñaEditText.text.toString()

            // Consulta la base de datos para verificar las credenciales del usuario
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val querySnapshot: QuerySnapshot = db.collection("Usuarios")
                        .whereEqualTo("usuario", usuario)
                        .whereEqualTo("contrasena", contraseña)
                        .get()
                        .await()

                    if (!querySnapshot.isEmpty) {
                        // Las credenciales son válidas, el usuario está autenticado
                        val documentSnapshot = querySnapshot.documents[0]
                        val idUsuario = documentSnapshot.id
                        val nombre: String = documentSnapshot.getString("nombre") ?: ""
                        val intent = Intent(this@MainActivity, PantallaPrincipal::class.java)
                        intent.putExtra("idUsuario", idUsuario)
                        intent.putExtra("usuario", nombre)
                        startActivity(intent)
                    } else {
                        // Las credenciales son inválidas, muestra un mensaje de error
                        Toast.makeText(this@MainActivity, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    // Ocurrió un error al acceder a la base de datos
                    Toast.makeText(this@MainActivity, "Error de base de datos", Toast.LENGTH_SHORT).show()
                }
            }
        } //fin setOnClickListener de iniciarSesion

        crearCuentaTextView.setOnClickListener {
            val intent = Intent(this@MainActivity, crearCuenta::class.java)
            startActivity(intent)
        }

        recuperarContrasena.setOnClickListener {
            val intent = Intent(this@MainActivity, olvideContrasena::class.java)
            startActivity(intent)
        }
    }
}