package com.example.buildyourbd

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import java.util.Locale

class GimnasiosActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var gimnasiosHelper: GimnasiosHelper

    private var userLat = 0.0
    private var userLon = 0.0
    private var currentGyms: List<Gimnasio> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gimnasio)

        gimnasiosHelper = GimnasiosHelper()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val container = findViewById<LinearLayout>(R.id.gimnasiosContainer)
        val btnFind = findViewById<Button>(R.id.btnFind)
        val btnMapa = findViewById<Button>(R.id.btnMapa)
        val btnBack = findViewById<Button>(R.id.btnBack)

        btnFind.setOnClickListener { buscarGimnasios(container) }
        btnMapa.setOnClickListener {
            if (currentGyms.isNotEmpty()) abrirMapa()
            else mensaje("No hay gimnasios para mostrar en el mapa")
        }
        btnBack.setOnClickListener { finish() }
    }

    private fun buscarGimnasios(container: LinearLayout) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { loc: Location? ->
            if (loc != null) {
                userLat = loc.latitude
                userLon = loc.longitude
                filtrarYMostrar(container)
            } else {
                solicitarUbicacionActual(container)
            }
        }.addOnFailureListener { e -> mensaje("Error al obtener ubicación: ${e.message}") }
    }

    private fun solicitarUbicacionActual(container: LinearLayout) {
        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            .setMaxUpdates(1)
            .build()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            mensaje("Permiso de ubicación no concedido")
            return
        }

        fusedLocationClient.requestLocationUpdates(request, object : LocationCallback() {
            override fun onLocationResult(res: LocationResult) {
                res.lastLocation?.let { loc ->
                    userLat = loc.latitude
                    userLon = loc.longitude
                    filtrarYMostrar(container)
                } ?: mensaje("No se pudo obtener ubicación actual")
                fusedLocationClient.removeLocationUpdates(this)
            }
        }, Looper.getMainLooper())
    }

    private fun filtrarYMostrar(container: LinearLayout) {
        currentGyms = gimnasiosHelper.filtrarGimnasiosCercanos(userLat, userLon)
        container.removeAllViews()

        if (currentGyms.isNotEmpty()) {
            currentGyms.forEach { gym ->
                val distancia = String.format(Locale.US, "%.2f km", gimnasiosHelper.calcularDistancia(userLat, userLon, gym.latitud, gym.longitud))
                val tv = TextView(this).apply {
                    text = "${gym.nombre} – $distancia\n${gym.direccion}"
                    setPadding(0, 8, 0, 8)
                }
                container.addView(tv)
            }
        } else {
            mensaje("No hay gimnasios cercanos en tu ubicación")
        }
    }

    private fun abrirMapa() {
        val gymsUri = currentGyms.joinToString("|") { "${it.latitud},${it.longitud}(${Uri.encode(it.nombre)})" }
        val uriString = "geo:$userLat,$userLon?q=$gymsUri"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uriString)).apply { setPackage("com.google.android.apps.maps") }

        if (intent.resolveActivity(packageManager) != null) startActivity(intent)
        else mensaje("No se encontró aplicación de mapas")
    }

    private fun mensaje(texto: String) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show()
    }
}
