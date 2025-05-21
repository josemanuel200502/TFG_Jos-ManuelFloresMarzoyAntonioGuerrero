package com.example.buildyourbd

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Alimentos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alimentos)

        // Botón Volver
        val btnBackAlimentos = findViewById<Button>(R.id.btnBackAlimentos)
        btnBackAlimentos.setOnClickListener {
            val intent = Intent(this, AppActivity::class.java)
            startActivity(intent)
            finish() // Cierra la actividad actual para evitar acumulaciones en la pila
        }
    }
}
