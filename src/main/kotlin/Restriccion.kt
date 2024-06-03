@file:Suppress("SpellCheckingInspection")

abstract class Restriccion {

    private val acciones: MutableList<Accion> = mutableListOf()

    fun agregarAcciones(accionesNuevas: MutableList<Accion>) {
        acciones.addAll(accionesNuevas)
    }

    fun ejecutarRevision(programa: Programa, canal: Canal) {
        if(!puedeEmitir(programa)) acciones.forEach { it.execute(canal) }
    }

    abstract fun puedeEmitir(programa: Programa): Boolean
}

class RatingAlto(private val ratingMinimo: Double) : Restriccion() {
    override fun puedeEmitir(programa: Programa): Boolean = promedioUltimasCincoEmisiones(programa) >= ratingMinimo
    private fun promedioUltimasCincoEmisiones(programa: Programa) = programa.ratingUltimasCincoEmisiones.average()
}

class LimiteConductores (private val conductoresMaximos: Int) : Restriccion() {
    override fun puedeEmitir(programa: Programa): Boolean = programa.conductores.size <= conductoresMaximos
}

class ConductoresEspecificos(private val setConductores: MutableSet<Conductor>) : Restriccion() {
    override fun puedeEmitir(programa: Programa): Boolean =
        programa.conductores.any { conductorOriginal -> setConductores.any { conductorCheck -> conductorCheck.nombre == conductorOriginal.nombre } }
}

class LimitePresupuesto (private val presupuestoLimite: Int) : Restriccion() {
    override fun puedeEmitir(programa: Programa): Boolean = programa.presupuestoBase <= presupuestoLimite
}

class RestriccionOrCompuesta(private val restricciones: MutableSet<Restriccion>) : Restriccion() {
    override fun puedeEmitir(programa: Programa) =
        restricciones.any { it.puedeEmitir(programa) }
}

class RestriccionAndCompuesta(private val restricciones: MutableSet<Restriccion>) : Restriccion() {
    override fun puedeEmitir(programa: Programa) =
        restricciones.all { it.puedeEmitir(programa) }
}






