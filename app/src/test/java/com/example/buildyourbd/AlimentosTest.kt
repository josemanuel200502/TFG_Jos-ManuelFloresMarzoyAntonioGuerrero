package com.example.buildyourbd

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AlimentosTest {
    private lateinit var alimentosActivity: AlimentosActivity

    @Before
    fun setUp() {
        alimentosActivity = AlimentosActivity()
    }

    @Test
    fun testGuardarAlimento() {
        alimentosActivity.guardarAlimento("Manzana", 95)
        val caloriasGuardadas = alimentosActivity.sharedPreferences.getInt("totalCalorias", 0)

        assertEquals(95, caloriasGuardadas) // Verificamos si el almacenamiento funciona
    }

    @Test
    fun testValoresLimiteCalorias() {
        alimentosActivity.guardarAlimento("Agua", 0) // Mínimo válido
        assertEquals(0, alimentosActivity.sharedPreferences.getInt("totalCalorias", 0))

        alimentosActivity.guardarAlimento("Pizza", 5000) // Límite extremo
        assertEquals(5000, alimentosActivity.sharedPreferences.getInt("totalCalorias", 0))
    }

}
