package com.example.buildyourbd

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RecetasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recetas)

        // Botón Volver
        val btnBackRecetas = findViewById<Button>(R.id.btnBackRecetas)
        btnBackRecetas.setOnClickListener {
            val intent = Intent(this, AppActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Botón Ver Detalle - Ensalada
        val btnDetalleEnsalada = findViewById<Button>(R.id.btnVerDetalleEnsalada)
        btnDetalleEnsalada.setOnClickListener {
            abrirDetalleReceta(
                "Ensalada Saludable",
                "1. Lava los ingredientes.\n2. Corta en pedazos.\n3. Mezcla todo con aliño.",
                "https://www.youtube.com/embed/rYgJskIIeC0" // Formato correcto
            )
        }

        // Botón Ver Detalle - Smoothie
        val btnDetalleSmoothie = findViewById<Button>(R.id.btnVerDetalleSmoothie)
        btnDetalleSmoothie.setOnClickListener {
            abrirDetalleReceta(
                "Smoothie Refrescante",
                "1. Agrega frutas en la licuadora.\n2. Mezcla con leche o yogur.\n3. Sirve frío.",
                "https://www.youtube.com/embed/-jdJdDT_A10"
            )
        }
    }

    private fun abrirDetalleReceta(titulo: String, instrucciones: String, videoUrl: String) {
        val intent = Intent(this, RecetaDetalleActivity::class.java)
        intent.putExtra("recetaTitulo", titulo)
        intent.putExtra("recetaInstrucciones", instrucciones)
        intent.putExtra("recetaVideoUrl", videoUrl)
        startActivity(intent)
    }
}
