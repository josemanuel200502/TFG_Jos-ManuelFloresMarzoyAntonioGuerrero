package com.example.buildyourbd

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Entrenamientos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrenamientos)


        // Botón Volver
        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent = Intent(this, AppActivity::class.java)
            startActivity(intent)
            finish() // Cierra la actividad actual para evitar acumulaciones en la pila
        }
    }
}


