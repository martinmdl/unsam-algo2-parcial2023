@file:Suppress("SpellCheckingInspection")

class Programa(
    var titulo: String,
    var conductores: MutableSet<Conductor>,
    var presupuestoBase: Int,
    var sponsor: String,
    var diaTransmision: DiaSemana,
    var duracion: Int,
    var ratingUltimasCincoEmisiones: MutableList<Int>,
) {

    private var restricciones: MutableList<Restriccion> = mutableListOf()

    fun agregarRestricciones(restriccionesNuevas: MutableList<Restriccion>) {
        restricciones.addAll(restriccionesNuevas)
    }

    fun ejecutarRevision(canal: Canal) {
        val primeraRestriccion = restricciones.find { it.puedeEmitir(this) }
        primeraRestriccion?.ejecutarRevision(this, canal)
    }

    fun getPrimerConductor(): Conductor = conductores.first()

}

enum class DiaSemana {
    LUNES,
    MARTES,
    MIERCOLES,
    JUEVES,
    VIERNES,
    SABADO,
    DOMINGO
}

data class Conductor(val nombre: String, val email: String)

