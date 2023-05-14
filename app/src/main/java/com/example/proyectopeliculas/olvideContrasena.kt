package com.example.proyectopeliculas

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class olvideContrasena : AppCompatActivity() {

    private lateinit var nombreEditText: EditText
    private lateinit var usuarioEditText: EditText
    private lateinit var correoEditText: EditText
    private lateinit var nuevaContrasenaEditText: EditText
    private lateinit var actualizarContrasenaButton: Button
    private val db = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_olvide_contrasena)

        nombreEditText = findViewById(R.id.editTextNombreRecuperacion)
        usuarioEditText = findViewById(R.id.editTextUsuarioRecuperacion)
        correoEditText = findViewById(R.id.editTextCorreoRecuperacion)
        nuevaContrasenaEditText = findViewById(R.id.editTextNuevaContrasena)
        actualizarContrasenaButton = findViewById(R.id.buttonRecuperarContrasena)

        actualizarContrasenaButton.setOnClickListener {
            val nombre = nombreEditText.text.toString().trim()
            val usuario = usuarioEditText.text.toString().trim()
            val correo = correoEditText.text.toString().trim()
            val nuevaContrasena = nuevaContrasenaEditText.text.toString().trim()

            if (validarCampos(nombre, usuario, correo, nuevaContrasena)) {
                recuperarContrasena(nombre, usuario, correo, nuevaContrasena)
            }
        }
    }

    private fun validarCampos(nombre: String, usuario: String, correo: String, nuevaContrasena: String): Boolean {
        if (nombre.isEmpty() || usuario.isEmpty() || correo.isEmpty() || nuevaContrasena.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun recuperarContrasena(nombre: String, usuario: String, correo: String, nuevaContrasena: String) {
        // Verificar la existencia de una cuenta con los datos ingresados
        db.collection("Usuarios")
            .whereEqualTo("nombre", nombre)
            .whereEqualTo("usuario", usuario)
            .whereEqualTo("correo", correo)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    // Actualizar la contraseña en la base de datos
                    val usuarioDoc = querySnapshot.documents[0]
                    val usuarioId = usuarioDoc.id

                    val actualizacionData = hashMapOf(
                        "contrasena" to nuevaContrasena
                    )

                    db.collection("Usuarios")
                        .document(usuarioId)
                        .update(actualizacionData as Map<String, Any>)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this,
                                "Contraseña actualizada exitosamente",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish() // Cerrar la actividad de recuperación de contraseña
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this,
                                "Error al actualizar la contraseña",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    Toast.makeText(
                        this,
                        "Los datos ingresados no corresponden a una cuenta existente",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al recuperar los datos", Toast.LENGTH_SHORT).show()
            }
    }
}