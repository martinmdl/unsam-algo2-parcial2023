@file:Suppress("SpellCheckingInspection")

import kotlin.random.Random

interface Accion {
    fun execute(): Programa
}

class Nula : Accion { // Null Object Pattern
    override fun execute(): Programa {
        return Programa(
            titulo = "",
            conductores = mutableSetOf(),
            presupuestoBase = 0,
            sponsor = "",
            diaTransmision = DiaSemana.LUNES,
            duracion = 0,
            ratingUltimasCincoEmisiones = mutableListOf(),
        )
    }
}

class Dividir(private val programa: Programa) : Accion {

    override fun execute(): Programa {
        return Programa(
            titulo = this.dividirTitulo(),
            conductores = this.dividirConductores(),
            presupuestoBase = this.dividirPresupuesto(),
            sponsor = programa.sponsor,
            diaTransmision = programa.diaTransmision,
            duracion = this.dividirDuracion(),
            ratingUltimasCincoEmisiones = mutableListOf(),
        )
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

class Eliminar(private val programa: Programa) : Accion {

//    override fun execute(): Programa {
//       return Programa(
//            titulo = "Los Simpsons",
//            conductores = mutableSetOf(Conductor("Homero")),
//            presupuestoBase = 2333,
//            sponsor = "UP",
//            diaTransmision = DiaSemana.DOMINGO,
//            duracion = 60,
//            ratingUltimasCincoEmisiones = mutableListOf(),
//       )
//    }

    override fun execute(): Programa {
        programa.titulo = "Los Simpsons"
        programa.conductores = mutableSetOf(Conductor("Homero"))
        programa.presupuestoBase = 2333
        programa.sponsor = "UP"
        programa.diaTransmision = DiaSemana.DOMINGO
        programa.duracion = 60
        programa.ratingUltimasCincoEmisiones = mutableListOf()

        return programa
    }
}

class Fusionar(private val programa: Programa, private val grilla: MutableList<Programa>) : Accion {

//    override fun execute(): Programa {
//
//        grilla.add(grilla.first())
//
//        return Programa(
//            titulo = this.tituloRandom(),
//            conductores = this.primerosConductores(grilla),
//            presupuestoBase = this.menorPresupeusto(grilla),
//            sponsor = this.sponsorRandom(grilla),
//            diaTransmision = programa.diaTransmision,
//            duracion = this.duracionTotal(grilla),
//            ratingUltimasCincoEmisiones = mutableListOf(),
//        )
//    }

    private val siguiente: Int = grilla.indexOf(programa) + 1

    override fun execute(): Programa {

        grilla.add(grilla.first())

        programa.titulo = this.tituloRandom()
        programa.conductores = this.primerosConductores(grilla)
        programa.presupuestoBase = this.menorPresupeusto(grilla)
        programa.sponsor = this.sponsorRandom(grilla)
        programa.diaTransmision = programa.diaTransmision
        programa.duracion = this.duracionTotal(grilla)
        programa.ratingUltimasCincoEmisiones = mutableListOf()

        return programa
    }

    private fun tituloRandom(): String =
        if(Random.nextBoolean()) "Impacto total" else "Un buen día"

    private fun primerosConductores(grillaAux: MutableList<Programa>): MutableSet<Conductor> =
        mutableSetOf(programa.getPrimerConductor(), grillaAux[siguiente].getPrimerConductor())

    private fun menorPresupeusto(grillaAux: MutableList<Programa>): Int =
        minOf(programa.presupuestoBase, grillaAux[siguiente].presupuestoBase)

    private fun sponsorRandom(grillaAux: MutableList<Programa>): String =
        if(Random.nextBoolean()) programa.sponsor else grillaAux[siguiente].sponsor

    private fun duracionTotal(grillaAux: MutableList<Programa>): Int =
        programa.duracion + grillaAux[siguiente].duracion
}

class AdelantarUnDia(private val programa: Programa) : Accion {

    override fun execute(): Programa {

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
        return programa
    }
}

