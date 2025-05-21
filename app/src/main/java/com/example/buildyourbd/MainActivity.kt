package com.example.buildyourbd

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var loginEmail: EditText
    private lateinit var loginPass: EditText
    private lateinit var entrarButton: Button
    private lateinit var invitadoButton: Button
    private lateinit var registro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar DB (esto parece solo para crearla por primera vez)
        val dbHelper = BBDD(this)
        dbHelper.writableDatabase.close()

        // Vinculación con elementos de UI
        loginEmail = findViewById(R.id.loginEmail)
        loginPass = findViewById(R.id.loginPass)
        entrarButton = findViewById(R.id.entrarButton)
        invitadoButton = findViewById(R.id.invitadoButton)
        registro = findViewById(R.id.registro)

        // Listeners
        entrarButton.setOnClickListener { login() }
        invitadoButton.setOnClickListener { loginAsGuest() }
        registro.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun login() {
        val email = loginEmail.text.toString()
        val password = loginPass.text.toString()

        val prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val savedEmail = prefs.getString("email", null)
        val savedPassword = prefs.getString("contrasena", null)

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (email == savedEmail && password == savedPassword) {
            Toast.makeText(this, "Bienvenido, $savedEmail", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, AppActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginAsGuest() {
        Toast.makeText(this, "Entrando como invitado", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, AppActivity::class.java))
        finish()
    }
}
