package com.example.buildyourbd

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Botón de volver al inicio de sesión
        val volverButton = findViewById<ImageButton>(R.id.volverButton)
        volverButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Cierra `RegisterActivity` para evitar que quede en el historial
        }

        // Botón de registro
        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.registerNombre).text.toString()
            val email = findViewById<EditText>(R.id.registerEmail).text.toString()
            val contrasena = findViewById<EditText>(R.id.registerContrasenia).text.toString()

            if (nombre.isEmpty() || email.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Guardar datos en SharedPreferences
            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("nombre", nombre)
            editor.putString("email", email)
            editor.putString("contrasena", contrasena)
            editor.apply()

            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()

            // Redirigir al inicio de sesión
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
