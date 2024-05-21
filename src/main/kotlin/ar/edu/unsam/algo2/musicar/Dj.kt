@file:Suppress("SpellCheckingInspection")
package ar.edu.unsam.algo2.musicar

import java.time.LocalDate

class Dj (
    var saldo: Double,
    private var fechaInicio: LocalDate,
    var dedicacionPlena: Boolean
) {

    fun puedeAlquilar(equipo: Equipo): Boolean = equipo.costoAlquiler() <= saldo

    fun alquilar(equipo: Equipo) {
        equipo.alquilarA(this)
    }

    fun aumentarSaldo(valor: Double) {
        saldo += valor
    }

    fun aniosDeExperiencia(): Int = LocalDate.now().year - fechaInicio.year
}