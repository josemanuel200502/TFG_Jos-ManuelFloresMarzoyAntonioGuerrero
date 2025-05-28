package com.example.buildyourbd

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class RecordatoriosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recordatorios)

        val btnBackRecordatorios = findViewById<Button>(R.id.btnBackRecordatorios)
        val btnNuevoRecordatorio = findViewById<Button>(R.id.btnNuevoRecordatorio)
        val txtRecordatorio = findViewById<EditText>(R.id.inputRecordatorio)
        val msgRecordatorio = findViewById<TextView>(R.id.emptyMsg)

        // Cargar recordatorio guardado al iniciar la pantalla
        cargarRecordatorio()

        btnNuevoRecordatorio.setOnClickListener {
            val nuevoRecordatorio = txtRecordatorio.text.toString().trim()
            if (nuevoRecordatorio.isNotEmpty()) {
                guardarRecordatorio(nuevoRecordatorio)
                cargarRecordatorio()
                txtRecordatorio.text.clear() // Limpiar el campo después de agregar
                Toast.makeText(this, "Recordatorio agregado!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Por favor, ingresa un recordatorio válido.", Toast.LENGTH_SHORT).show()
            }
        }


        btnBackRecordatorios.setOnClickListener {
            val intent = Intent(this, AppActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun guardarRecordatorio(nuevoRecordatorio: String) {
        val prefs = getSharedPreferences("RecordatoriosPrefs", MODE_PRIVATE)
        val recordatorios = prefs.getStringSet("recordatorios", mutableSetOf()) ?: mutableSetOf()

        recordatorios.add(nuevoRecordatorio) // Agregar nuevo recordatorio a la lista

        val editor = prefs.edit()
        editor.putStringSet("recordatorios", recordatorios)
        editor.apply()
    }


    private fun cargarRecordatorio() {
        val prefs = getSharedPreferences("RecordatoriosPrefs", MODE_PRIVATE)
        val recordatorios = prefs.getStringSet("recordatorios", mutableSetOf())

        val emptyMsg = findViewById<TextView>(R.id.emptyMsg)
        if (recordatorios.isNullOrEmpty()) {
            emptyMsg.text = "No tienes recordatorios aún."
        } else {
            emptyMsg.text = recordatorios.joinToString("\n") // Mostrar lista de recordatorios
        }
    }

}
