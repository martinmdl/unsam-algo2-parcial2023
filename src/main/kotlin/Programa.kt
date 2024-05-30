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

    private var restriccion: Restriccion = SinRestriccion()

    fun cambiarRestriccion(restriccionNueva: Restriccion) {
        restriccion = restriccionNueva
    }

    fun estaEnRegla(): Boolean = restriccion.puedeEmitir(this)

    fun getPrimerConductor(): Conductor = conductores.first()

}

