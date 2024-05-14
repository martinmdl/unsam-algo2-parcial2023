@file:Suppress("SpellCheckingInspection")

import java.time.LocalDate

class Dj (
    var saldo: Double,
    private var fechaInicio: LocalDate,
    var dedicacionPlena: Boolean
) {

    private fun puedeAlquilar(equipo: Equipo): Boolean = equipo.costoAlquiler() <= saldo

    fun alquilar(equipo: Equipo) {
        if (!puedeAlquilar(equipo)) throw BusinessException("No tiene suficiente saldo")
        saldo -= equipo.costoAlquiler()
    }

    fun aumentarSaldo(valor: Double) {
        saldo += valor
    }

    fun aniosDeExperiencia(): Int = LocalDate.now().year - fechaInicio.year
}