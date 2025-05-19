package com.example.buildyourbd

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var loginEmail: EditText? = null
    private var loginPass: EditText? = null
    private var entrarButton: Button? = null
    private var buttonGoogle: Button? = null
    private var invitadoButton: Button? = null
    private var registro: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Muestra la ruta de la base de datos
        val dbHelper = BBDD(this)
        val rutaDB = dbHelper.getDatabasePath(this) // Aquí pasamos el contexto
        Log.d("RutaDB", "Ruta de la base de datos: $rutaDB")

        // Inicializa los elementos del layout
        loginEmail = findViewById(R.id.loginEmail)
        loginPass = findViewById(R.id.loginPass)
        entrarButton = findViewById(R.id.entrarButton)
        invitadoButton = findViewById(R.id.invitadoButton)
        registro = findViewById(R.id.registro)

        // Configura los listeners
        entrarButton?.setOnClickListener { login() }
        invitadoButton?.setOnClickListener { loginAsGuest() }
        buttonGoogle?.setOnClickListener { loginWithGoogle() }
        registro?.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val email = loginEmail?.text.toString()
        val password = loginPass?.text.toString()

        // Obtener credenciales guardadas
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val savedEmail = sharedPreferences.getString("email", null)
        val savedPassword = sharedPreferences.getString("contrasena", null)

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (email == savedEmail && password == savedPassword) {
            Toast.makeText(this, "Bienvenido, $savedEmail", Toast.LENGTH_SHORT).show()
            // Aquí puedes navegar a la pantalla principal
        } else {
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginAsGuest() {
        Toast.makeText(this, "Iniciando sesión como invitado", Toast.LENGTH_SHORT).show()
    }

    private fun loginWithGoogle() {
        Toast.makeText(this, "Iniciando sesión con Google", Toast.LENGTH_SHORT).show()
    }
}
