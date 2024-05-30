@file:Suppress("SpellCheckingInspection")

interface Restriccion {
    fun puedeEmitir(programa: Programa): Boolean
}

class SinRestriccion : Restriccion { // Null Object Pattern
    override fun puedeEmitir(programa: Programa): Boolean = true
}

class RatingAlto(private val ratingMinimo: Double) : Restriccion {
    override fun puedeEmitir(programa: Programa): Boolean = promedioUltimasCincoEmisiones(programa) >= ratingMinimo
    private fun promedioUltimasCincoEmisiones(programa: Programa) = programa.ratingUltimasCincoEmisiones.average()
}

class LimiteConductores (private val conductoresMaximos: Int) : Restriccion {
    override fun puedeEmitir(programa: Programa): Boolean = programa.conductores.size <= conductoresMaximos
}

class ConductoresEspecificos(private val setConductores: MutableSet<Conductor>) : Restriccion {
    override fun puedeEmitir(programa: Programa): Boolean =
        programa.conductores.any { conductorOriginal -> setConductores.any { conductorCheck -> conductorCheck.nombre == conductorOriginal.nombre } }
}

class LimitePresupuesto (private val presupuestoLimite: Int) : Restriccion {
    override fun puedeEmitir(programa: Programa): Boolean = programa.presupuestoBase <= presupuestoLimite
}

class RestriccionesCombinadas(private val restricciones: MutableSet<Restriccion>, private val flagOpcion: Int) : Restriccion {

    override fun puedeEmitir(programa: Programa): Boolean = true
//    private fun primeraCondicion(programa: Programa) = restricciones.any { it.puedeEmitir(programa) }
//    private fun segundaCondicion(programa: Programa) = restricciones.all { it.puedeEmitir(programa) }
}






