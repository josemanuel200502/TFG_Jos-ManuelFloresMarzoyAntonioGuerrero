package com.example.buildyourbd

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AlimentosActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var txtListaAlimentos: TextView
    private lateinit var txtTotalCalorias: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alimentos)

        sharedPreferences = getSharedPreferences("AlimentosPrefs", MODE_PRIVATE)
        txtListaAlimentos = findViewById(R.id.txtListaAlimentos)
        txtTotalCalorias = findViewById(R.id.txtTotalCalorias)
        val inputAlimento = findViewById<EditText>(R.id.inputAlimento)
        val inputCalorias = findViewById<EditText>(R.id.inputCalorias)
        val btnAgregar = findViewById<Button>(R.id.btnAgregarAlimento)

        // Cargar alimentos previos al iniciar la pantalla
        cargarAlimentos()

        btnAgregar.setOnClickListener {
            val alimento = inputAlimento.text.toString().trim()
            val calorias = inputCalorias.text.toString().trim().toIntOrNull()

            if (alimento.isNotEmpty() && calorias != null) {
                guardarAlimento(alimento, calorias)
                cargarAlimentos()
                inputAlimento.text.clear()
                inputCalorias.text.clear()
                Toast.makeText(this, "Alimento agregado!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Ingrese un alimento válido y sus calorías.", Toast.LENGTH_SHORT).show()
            }
        }

        val btnVolver = findViewById<Button>(R.id.btnBackAlimentos)
        btnVolver.setOnClickListener { finish() }
    }

    fun guardarAlimento(alimento: String, calorias: Int) {
        val alimentos = sharedPreferences.getStringSet("alimentos", mutableSetOf()) ?: mutableSetOf()
        val editor = sharedPreferences.edit()

        alimentos.add("$alimento - $calorias kcal")
        editor.putStringSet("alimentos", alimentos)

        val totalCalorias = (sharedPreferences.getInt("totalCalorias", 0) + calorias)
        editor.putInt("totalCalorias", totalCalorias)
        editor.apply()
    }

    private fun cargarAlimentos() {
        val alimentos = sharedPreferences.getStringSet("alimentos", mutableSetOf()) ?: mutableSetOf()
        txtListaAlimentos.text = if (alimentos.isEmpty()) "No has registrado alimentos aún." else alimentos.joinToString("\n")

        val totalCalorias = sharedPreferences.getInt("totalCalorias", 0)
        txtTotalCalorias.text = "Calorías hoy: $totalCalorias kcal"
    }
}
