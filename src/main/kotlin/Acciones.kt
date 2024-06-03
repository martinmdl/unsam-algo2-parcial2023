@file:Suppress("SpellCheckingInspection")

import kotlin.random.Random

abstract class Accion {
    private val observers: MutableSet<ObservadorNuevosProgramas> = mutableSetOf()
    fun agregarObs(obs: ObservadorNuevosProgramas) { observers.add(obs) }
    fun eliminarObs(obs: ObservadorNuevosProgramas) { observers.remove(obs) }

    abstract fun execute(canal: Canal)
}

class Dividir(private val programa: Programa) : Accion() {

    override fun execute(canal: Canal) {
        val programa1 = Programa(
            titulo = this.dividirTitulo(),
            conductores = this.dividirConductores(),
            presupuestoBase = this.dividirPresupuesto(),
            sponsor = programa.sponsor,
            diaTransmision = programa.diaTransmision,
            duracion = this.dividirDuracion(),
            ratingUltimasCincoEmisiones = mutableListOf(),
        )

        canal.agregarPrograma(programa1)
    }

    companion object { private const val MITAD = 0.5 }

    private fun dividirConductores(): MutableSet<Conductor> {
        val mitad = (programa.conductores.size * MITAD).toInt()
        val segundaMitad = programa.conductores.drop(mitad).toMutableSet()
        programa.conductores = programa.conductores.take(mitad).toMutableSet()
        return segundaMitad
    }

    private fun dividirPresupuesto(): Int {
        val mitadPresupuesto: Int = (programa.presupuestoBase * MITAD).toInt()
        programa.presupuestoBase = mitadPresupuesto
        return mitadPresupuesto
    }

    private fun dividirDuracion(): Int {
        val mitadDuracion: Int = (programa.duracion * MITAD).toInt()
        programa.duracion = mitadDuracion
        return mitadDuracion
    }

    private fun dividirTitulo(): String {
        val palabras = programa.titulo.split(" ")
        programa.titulo = "${palabras[0]} en el aire!"
        val titulo2 = palabras[1]
        return if(titulo2.isNotBlank()) {
            palabras[1]
        } else {
            "Programa sin nombre"
        }
    }
}

class Eliminar(private val programa: Programa) : Accion() {

    override fun execute(canal: Canal) {
        programa.titulo = "Los Simpsons"
        programa.conductores = mutableSetOf(Conductor("Homero", "homero@gmail.com"))
        programa.presupuestoBase = 2333
        programa.sponsor = "UP"
        programa.diaTransmision = DiaSemana.DOMINGO
        programa.duracion = 60
        programa.ratingUltimasCincoEmisiones = mutableListOf()
    }
}

class Fusionar(private val programa: Programa) : Accion() {

    override fun execute(canal: Canal) {

        val siguiente: Int = canal.grilla.indexOf(programa) + 1
        canal.grilla.add(canal.grilla.first())

        programa.titulo = this.tituloRandom()
        programa.conductores = this.primerosConductores(canal.grilla, siguiente)
        programa.presupuestoBase = this.menorPresupeusto(canal.grilla, siguiente)
        programa.sponsor = this.sponsorRandom(canal.grilla, siguiente)
        programa.diaTransmision = programa.diaTransmision
        programa.duracion = this.duracionTotal(canal.grilla, siguiente)
        programa.ratingUltimasCincoEmisiones = mutableListOf()

        canal.grilla.removeAt(siguiente)
    }

    private fun tituloRandom(): String =
        if(Random.nextBoolean()) "Impacto total" else "Un buen día"

    private fun primerosConductores(grillaAux: MutableList<Programa>, siguiente: Int): MutableSet<Conductor> =
        mutableSetOf(programa.getPrimerConductor(), grillaAux[siguiente].getPrimerConductor())

    private fun menorPresupeusto(grillaAux: MutableList<Programa>, siguiente: Int): Int =
        minOf(programa.presupuestoBase, grillaAux[siguiente].presupuestoBase)

    private fun sponsorRandom(grillaAux: MutableList<Programa>, siguiente: Int): String =
        if(Random.nextBoolean()) programa.sponsor else grillaAux[siguiente].sponsor

    private fun duracionTotal(grillaAux: MutableList<Programa>, siguiente: Int): Int =
        programa.duracion + grillaAux[siguiente].duracion
}

class AdelantarUnDia(private val programa: Programa) : Accion() {

    override fun execute(canal: Canal) {

        val dias = listOf(
            DiaSemana.LUNES,
            DiaSemana.MARTES,
            DiaSemana.MIERCOLES,
            DiaSemana.JUEVES,
            DiaSemana.VIERNES,
            DiaSemana.SABADO,
            DiaSemana.DOMINGO,
            DiaSemana.LUNES,
        )

        val siguiente = dias.indexOf(programa.diaTransmision) + 1
        programa.diaTransmision = dias[siguiente]
    }
}
