package com.example.proyectopeliculas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class crearCuenta : AppCompatActivity() {

    private lateinit var nombreEditText: EditText
    private lateinit var correoEditText: EditText
    private lateinit var usuarioEditText: EditText
    private lateinit var contraseñaEditText: EditText
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)

        nombreEditText = findViewById(R.id.editTextNombreCompleto)
        correoEditText = findViewById(R.id.editTextCorreo)
        usuarioEditText = findViewById(R.id.editTextUsuario)
        contraseñaEditText = findViewById(R.id.editTextaContrasena)

        val crearCuentaButton: Button = findViewById(R.id.buttonCrearCuenta)
        crearCuentaButton.setOnClickListener {
            val nombre: String = nombreEditText.text.toString()
            val correo: String = correoEditText.text.toString()
            val usuario: String = usuarioEditText.text.toString()
            val contraseña: String = contraseñaEditText.text.toString()

            if (validarCampos(nombre, correo, usuario, contraseña)) {
                registrarCuenta(nombre, correo, usuario, contraseña)
            }
        }
    }

    private fun validarCampos(nombre: String, correo: String, usuario: String, contraseña: String): Boolean {
        if (TextUtils.isEmpty(nombre)) {
            mostrarMensajeError("Ingrese el nombre completo")
            return false
        }

        if (TextUtils.isEmpty(correo) || !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            mostrarMensajeError("Ingrese un correo electrónico válido")
            return false
        }

        if (TextUtils.isEmpty(usuario)) {
            mostrarMensajeError("Ingrese un nombre de usuario")
            return false
        }

        if (TextUtils.isEmpty(contraseña)) {
            mostrarMensajeError("Ingrese una contraseña")
            return false
        }

        return true
    }

    private fun registrarCuenta(nombre: String, correo: String, usuario: String, contraseña: String) {
        val usuarioData = HashMap<String, Any>()
        usuarioData["nombre"] = nombre
        usuarioData["correo"] = correo
        usuarioData["usuario"] = usuario
        usuarioData["contrasena"] = contraseña

        db.collection("Usuarios")
            .add(usuarioData)
            .addOnSuccessListener {
                mostrarMensaje("Cuenta creada exitosamente")
                finish() // Cerrar la actividad de registro después de crear la cuenta
            }
            .addOnFailureListener { e ->
                mostrarMensajeError("Error al crear la cuenta")
            }
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun mostrarMensajeError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}