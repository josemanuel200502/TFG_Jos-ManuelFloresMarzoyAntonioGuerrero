package com.example.buildyourbd

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*

class Gimnasios(private val context: Context) {

    // Cliente de ubicación fusionada de Google Play Services
    private val fusedLocationClient = LocationServices
        .getFusedLocationProviderClient(context)

    /**
     * Obtiene la última ubicación conocida del dispositivo.
     *
     * @param onSuccess Callback que recibe (latitud, longitud) si todo va bien.
     * @param onError   Callback que recibe un mensaje si falta permiso,
     *                  la ubicación es null o hay algún fallo.
     */
    @SuppressLint("MissingPermission") // ya comprobamos permiso antes de llamar
    fun obtenerUbicacionUsuario(
        onSuccess: (Double, Double) -> Unit,
        onError: (String) -> Unit
    ) {
        // 1) Verificar permiso en tiempo de ejecución
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            onError("Permiso de ubicación no concedido")
            return
        }

        // 2) Pedir última ubicación conocida
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    onSuccess(location.latitude, location.longitude)
                } else {
                    onError("Ubicación no disponible. Activa el GPS o intenta de nuevo.")
                }
            }
            .addOnFailureListener { e ->
                onError("Error al obtener ubicación: ${e.message}")
            }
    }

    /**
     * Fuerza una única actualización de alta precisión.
     *
     * @param onSuccess Callback que recibe (latitud, longitud).
     * @param onError   Callback que recibe un mensaje en caso de error.
     */
    @SuppressLint("MissingPermission")
    fun solicitarUbicacionActualizada(
        onSuccess: (Double, Double) -> Unit,
        onError: (String) -> Unit
    ) {
        // Creamos la petición de alta precisión
        val request = LocationRequest.create().apply {
            priority = Priority.PRIORITY_HIGH_ACCURACY
            numUpdates = 1
            interval = 0L
        }

        fusedLocationClient.requestLocationUpdates(
            request,
            object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    fusedLocationClient.removeLocationUpdates(this)
                    val loc = result.lastLocation
                    if (loc != null) {
                        onSuccess(loc.latitude, loc.longitude)
                    } else {
                        onError("No se obtuvo ubicación tras la solicitud")
                    }
                }
            },
            Looper.getMainLooper()
        )
    }
}
