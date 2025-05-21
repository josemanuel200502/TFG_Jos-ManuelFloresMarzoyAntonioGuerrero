package com.example.buildyourbd


    class GimnasiosHelper {
        private val gimnasios = listOf(
            Gimnasio("Gym A", "Calle 1", 40.4168, -3.7038),
            Gimnasio("Gym B", "Avenida 2", 40.4180, -3.7050),
            Gimnasio("Gym C", "Plaza 3", 40.4200, -3.7000),
            // Añade más gimnasios con sus coordenadas reales
        )

        fun filtrarGimnasiosCercanos(userLat: Double, userLon: Double, maxDistKm: Double = 5.0): List<Gimnasio> {
            return gimnasios.filter {
                calcularDistancia(userLat, userLon, it.latitud, it.longitud) <= maxDistKm
            }
        }

        fun calcularDistancia(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
            val radius = 6371.0 // km
            val dLat = Math.toRadians(lat2 - lat1)
            val dLon = Math.toRadians(lon2 - lon1)
            val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                    Math.sin(dLon / 2) * Math.sin(dLon / 2)
            val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
            return radius * c
        }
    }

