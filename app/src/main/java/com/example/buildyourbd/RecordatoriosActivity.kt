package com.example.buildyourbd

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RecordatoriosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recordatorios)

        // Botón Volver
        val btnBackRecordatorios = findViewById<Button>(R.id.btnBackRecordatorios)
        btnBackRecordatorios.setOnClickListener {
            val intent = Intent(this, AppActivity::class.java)
            startActivity(intent)
            finish() // Cierra la actividad actual para evitar acumulaciones en la pila
        }
    }
}
