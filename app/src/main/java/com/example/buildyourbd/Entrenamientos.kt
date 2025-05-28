package com.example.buildyourbd

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class Entrenamientos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrenamientos)

        // Encuentra la ProgressBar por su ID
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        // Simula un tiempo de carga y luego oculta la barra de progreso
        progressBar.postDelayed({
            progressBar.visibility = View.GONE
        }, 2000) // Oculta el ProgressBar después de 2 segundos

        // Botón Volver
        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent = Intent(this, AppActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 💪 Lógica para Cardio Básico
        configurarDespliegue(
            R.id.btnDesplegarCardio,
            R.id.contenedorCardio,
            R.id.webViewCardio,
            R.id.btnCompletarCardio,
            "https://www.youtube.com/embed/39Sn10y7HMM"
        )

        // 🏋️ Lógica para Fuerza en casa
        configurarDespliegue(
            R.id.btnDesplegarFuerza,
            R.id.contenedorFuerza,
            R.id.webViewFuerza,
            R.id.btnCompletarFuerza,
            "https://www.youtube.com/embed/7qVlC47BFTo"
        )

        // 🤸 Lógica para Estiramientos
        configurarDespliegue(
            R.id.btnDesplegarEstiramientos,
            R.id.contenedorEstiramientos,
            R.id.webViewEstiramientos,
            R.id.btnCompletarEstiramientos,
            "https://www.youtube.com/embed/jwdAPlpKwSw"
        )
    }

    //  Función para manejar el despliegue del contenido
    private fun configurarDespliegue(
        btnDesplegarId: Int,
        contenedorId: Int,
        webViewId: Int,
        btnCompletarId: Int,
        videoUrl: String
    ) {
        val btnDesplegar = findViewById<Button>(btnDesplegarId)
        val contenedor = findViewById<LinearLayout>(contenedorId)
        val webView = findViewById<WebView>(webViewId)
        val btnCompletar = findViewById<Button>(btnCompletarId)

        btnDesplegar.setOnClickListener {
            if (contenedor.visibility == View.GONE) {
                contenedor.visibility = View.VISIBLE

                // Configurar WebView para mostrar el video en modo embed
                webView.settings.javaScriptEnabled = true
                webView.settings.loadWithOverviewMode = true
                webView.settings.useWideViewPort = true
                webView.loadUrl(videoUrl)
            } else {
                contenedor.visibility = View.GONE
            }
        }

        btnCompletar.setOnClickListener {
            btnCompletar.text = "✅ Completado"
            btnCompletar.isEnabled = false
        }
    }
}
