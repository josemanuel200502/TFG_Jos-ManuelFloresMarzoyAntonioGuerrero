package com.example.buildyourbd

import android.os.Bundle
import android.view.View
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
        setContentView(R.layout.activity_main)  // Cambiado aquí

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
        registro?.setOnClickListener { goToRegister() }
    }

    private fun login() {
        val email = loginEmail?.text.toString()
        val password = loginPass?.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (email == "usuario@dominio.com" && password == "123456") {
            Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
            // Navega a la pantalla principal o dashboard aquí
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

    private fun goToRegister() {
        Toast.makeText(this, "Redirigiendo a registro", Toast.LENGTH_SHORT).show()
    }
}
