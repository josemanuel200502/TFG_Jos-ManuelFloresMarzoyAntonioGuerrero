package com.example.buildyourbd

import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class AppActivity : AppCompatActivity() {

    private lateinit var youtubeWebView: WebView
    private lateinit var botonInicio: ImageButton
    private lateinit var botonAjustes: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

        // Inicializamos WebView
        youtubeWebView = findViewById(R.id.youtubeWebView)
        youtubeWebView.webViewClient = WebViewClient()
        val webSettings: WebSettings = youtubeWebView.settings
        webSettings.javaScriptEnabled = true
        val videoUrl = "https://www.youtube.com/embed/dQw4w9WgXcQ"
        youtubeWebView.loadUrl(videoUrl)

        // Botones de la barra inferior
        botonInicio = findViewById(R.id.botonInicio)
        botonAjustes = findViewById(R.id.botonAjustes)

        botonInicio.setOnClickListener {
            // Recarga el video
            youtubeWebView.loadUrl(videoUrl)
        }
        // Botones del GridLayout
        findViewById<Button>(R.id.boton1).setOnClickListener {
            // Entrenamientos
            startActivity(Intent(this, Entrenamientos::class.java))
        }

        findViewById<Button>(R.id.boton2).setOnClickListener {
            // Recetas
            startActivity(Intent(this, RecetasActivity::class.java))
        }

        findViewById<Button>(R.id.boton3).setOnClickListener {
            // Comunidad
            startActivity(Intent(this, ComunidadActivity::class.java))
        }

        findViewById<Button>(R.id.boton4).setOnClickListener {
            // Recordatorios
            startActivity(Intent(this, RecordatoriosActivity::class.java))
        }

        findViewById<Button>(R.id.boton5).setOnClickListener {
            // Alimentos (Tracker)
            startActivity(Intent(this, Alimentos::class.java))
        }

    }
}
