import java.time.DayOfWeek
import kotlin.random.Random

class BusinessException(message: String) : Exception()

// #### PUNTO 3 ####
class Canal(val grilla: MutableList<Programa>) {

    private val observersNuevoPrograma = mutableSetOf<ObserverPrograma>()

    private val programasEnRevision = mutableSetOf<Programa>()

    fun definirRestriccionesPorPrograma(restricciones: MutableSet<Restriccion>, programa: Programa) {
        val programaPorModificar = grilla.find { it == programa } ?: throw BusinessException("$programa no está en la grilla")
        programaPorModificar.agregarRestricciones(restricciones)
    }

    fun definirAccionesPorRestriccion(restriccion: Restriccion, acciones: MutableSet<Accion>) {
        restriccion.agregarAccionesPorIncumplimiento(acciones)
    }

    fun agregarPrograma(programa: Programa) {
        grilla.add(programa)
        programaCreado(programa)
    }

    fun eliminarPrograma(programa: Programa) {
        grilla.remove(programa)
    }

    fun agregarARevision(programas: MutableSet<Programa>) {
        programasEnRevision.addAll(programas)
    }

    fun ejecutarRevision() {
        programasEnRevision.forEach { it.revisarRestricciones(this) }
    }

    private fun programaCreado(programa: Programa) {
        observersNuevoPrograma.forEach { it.ejecutar(programa) }
    }

    fun sincListRevision() {
        programasEnRevision.removeIf { !grilla.contains(it) }
    }

}

class Programa(
    val titulo: String,
    val conductores: MutableSet<Conductor>,
    val presupuesto: Int,
    val registroRating: MutableList<Double>,
    val dias: MutableSet<DayOfWeek>,
    val sponsors: MutableSet<String>,
    val duracion: Int
) {

    private val restricciones = mutableSetOf<Restriccion>()

    fun agregarRestricciones(restricciones: MutableSet<Restriccion>) {
        restricciones.addAll(restricciones)
    }

    fun revisarRestricciones(canal: Canal) {
        restricciones.first { !it.cumple(this) }.ejecutarAcciones(this, canal)
    }
}

data class Conductor(val nombre: String, val email: String)

// #### PUNTO 1 ####
abstract class Restriccion {

    private val acciones = mutableSetOf<Accion>()

    fun agregarAccionesPorIncumplimiento(acciones: MutableSet<Accion>) {
        acciones.addAll(acciones)
    }

    abstract fun cumple(programa: Programa): Boolean

    fun ejecutarAcciones(programa: Programa, canal: Canal) {
        acciones.forEach { it.ejecutar(programa, canal) }
    }
}

// #### PUNTO 1.1 ####
class RatingMinimo(private val ratingMin: Double) : Restriccion() {

    override fun cumple(programa: Programa): Boolean = promedioRatingActual(programa) > ratingMin

    private fun promedioRatingActual(programa: Programa): Double =
        programa.registroRating.takeLast(5).average()
}

// #### PUNTO 1.2 ####
class LimiteConductores(private val limiteConductores: Int) : Restriccion() {

    override fun cumple(programa: Programa): Boolean = programa.conductores.size <= limiteConductores
}

// #### PUNTO 1.3 ####
class ConductorEspecifico(private val nombreConductor: String) : Restriccion() {

    override fun cumple(programa: Programa): Boolean = programa.conductores.any { it.nombre == nombreConductor }
}

// #### PUNTO 1.4 ####
class PresupuestoExcedido(private val presupuestoMax: Double) : Restriccion() {

    override fun cumple(programa: Programa): Boolean = programa.presupuesto <= presupuestoMax
}

// #### PUNTO 1.5 ####
class CombinadaOr(private val restricciones: MutableSet<Restriccion>) : Restriccion() {

    override fun cumple(programa: Programa): Boolean = restricciones.any { it.cumple(programa) }
}

// #### PUNTO 1.6 ####
class CombinadaAnd(private val restricciones: MutableSet<Restriccion>) : Restriccion() {

    override fun cumple(programa: Programa): Boolean = restricciones.all { it.cumple(programa) }
}

// #### PUNTO 2 ####
interface Accion { fun ejecutar(programa: Programa, canal: Canal) }

// #### PUNTO 2.1 ####
class DividirEnDos : Accion {

    override fun ejecutar(programa: Programa, canal: Canal) {

        val nuevoPrograma1 = Programa(
            titulo = primeraMitadTitulo(programa),
            conductores = primeraMitadConductores(programa),
            presupuesto = dividirPresupuesto(programa),
            registroRating = mutableListOf(),
            dias = programa.dias,
            sponsors = programa.sponsors,
            duracion = dividirDuracion(programa),
        )

        val nuevoPrograma2 = Programa(
            titulo = segundaMitadTitulo(programa),
            conductores = segundaMitadConductores(programa),
            presupuesto = dividirPresupuesto(programa),
            registroRating = mutableListOf(),
            dias = programa.dias,
            sponsors = programa.sponsors,
            duracion = dividirDuracion(programa),
        )

        canal.eliminarPrograma(programa)
        canal.agregarPrograma(nuevoPrograma1)
        canal.agregarPrograma(nuevoPrograma2)
    }

    private fun primeraMitadTitulo(programa: Programa): String {
        val palabras = programa.titulo.split(" ")
        return "${palabras[0]} en el aire!"
    }

    private fun segundaMitadTitulo(programa: Programa): String {
        val palabras = programa.titulo.split(" ")
        return palabras[1].ifBlank { "Programa sin nombre" }
    }

    private fun primeraMitadConductores(programa: Programa): MutableSet<Conductor> {
        val mitad = programa.conductores.size / 2
        return programa.conductores.take(mitad).toMutableSet()
    }

    private fun segundaMitadConductores(programa: Programa): MutableSet<Conductor> {
        val mitad = programa.conductores.size / 2
        return programa.conductores.drop(mitad).toMutableSet()
    }

    private fun dividirPresupuesto(programa: Programa): Int = programa.presupuesto / 2

    private fun dividirDuracion(programa: Programa): Int = programa.duracion / 2
}

// #### PUNTO 2.2 ####
class ReemplazarPorLosSimpsons : Accion {

    override fun ejecutar(programa: Programa, canal: Canal) {

        val nuevoPrograma = Programa(
            titulo = "Los Simpsons",
            conductores = mutableSetOf(Conductor("Homero", "homero@fox.com")),
            presupuesto = 100,
            registroRating = mutableListOf(),
            dias = mutableSetOf(DayOfWeek.SATURDAY),
            sponsors = mutableSetOf("UP"),
            duracion = 120,
        )

        canal.eliminarPrograma(programa)
        canal.agregarPrograma(nuevoPrograma)
    }
}

// #### PUNTO 2.3 ####
class FusionarConElSiguiente : Accion {

    override fun ejecutar(programa: Programa, canal: Canal) {

        val nuevaGrilla = canal.grilla
        nuevaGrilla.add(nuevaGrilla.first())
        val siguienteIndice: Int = nuevaGrilla.indexOf(programa) + 1
        val siguientePrograma = nuevaGrilla[siguienteIndice]


        canal.grilla.removeLast()

        val nuevoPrograma = Programa(
            titulo = tituloAleatorio(),
            conductores = primerosConductores(programa, siguientePrograma),
            presupuesto = menorPresupuesto(programa, siguientePrograma),
            registroRating = mutableListOf(),
            dias = programa.dias,
            sponsors = sponsorAleatorio(programa, siguientePrograma),
            duracion = duracionAmbosProgramas(programa, siguientePrograma),
        )

        canal.eliminarPrograma(programa)
        canal.agregarPrograma(nuevoPrograma)
    }

    private fun duracionAmbosProgramas(programa: Programa, siguientePrograma: Programa): Int =
        programa.duracion + siguientePrograma.duracion

    private fun sponsorAleatorio(programa: Programa, siguientePrograma: Programa): MutableSet<String> =
        if(Random.nextBoolean()) programa.sponsors else siguientePrograma.sponsors

    private fun menorPresupuesto(programa: Programa, siguientePrograma: Programa): Int =
        minOf(programa.presupuesto, siguientePrograma.presupuesto)

    private fun primerosConductores(programa: Programa, siguientePrograma: Programa): MutableSet<Conductor> =
        mutableSetOf(programa.conductores.first(), siguientePrograma.conductores.first())

    private fun tituloAleatorio(): String =
        if(Random.nextBoolean()) "Impacto total" else "Un buen día"
}

// #### PUNTO 2.4 ####
class AdelantarUnDia : Accion {

    override fun ejecutar(programa: Programa, canal: Canal) {

        val nuevoPrograma = Programa(
            titulo = programa.titulo,
            conductores = programa.conductores,
            presupuesto = programa.presupuesto,
            registroRating = programa.registroRating,
            dias = adelantarCadaDia(programa),
            sponsors = programa.sponsors,
            duracion = programa.duracion,
        )

        canal.eliminarPrograma(programa)
        canal.agregarPrograma(nuevoPrograma)
    }

    private fun adelantarCadaDia(programa: Programa): MutableSet<DayOfWeek> {
        programa.dias.forEach { it + 1 }
        return programa.dias
    }
}

// #### PUNTO 3.4 ####
interface ObserverPrograma { fun ejecutar(programa: Programa) }

// #### PUNTO 3.4.1 ####
interface MailSender { fun sendMail(mail: Mail) }

data class Mail(val from: String, val to: String, val subject: String, val message: String)

class NotificarConductores(private val mailSender: MailSender) : ObserverPrograma {

    override fun ejecutar(programa: Programa) {
        val emails = programa.conductores.joinToString(", ") { it.email }
        mailSender.sendMail(Mail(
            from = "gerencia@telefe.com",
            to = emails,
            subject = "Oportunidad!",
            message = "Fuiste seleccionado para conducir ${programa.titulo}! Ponete en contacto con la gerencia."
        ))
    }
}

// #### PUNTO 3.4.2 ####
interface SMSSender { fun sendSMS(sms: SMS) }

data class SMS(val to: String, val message: String)

class PresupuestoAlto(private val smsSender: SMSSender) : ObserverPrograma {

    companion object { private const val PRESUPUESTO_LIMITE = 100000 }

    override fun ejecutar(programa: Programa) {
        if(programa.presupuesto > PRESUPUESTO_LIMITE) {
            smsSender.sendSMS(SMS(
                to = "Cliowin",
                message = "${programa.presupuesto} - ${programa.titulo} - CONSEGUIR SPONSOR URGENTE!"
            ))
        }
    }
}

// #### PUNTO 3.4.3 ####
class EliminarDeRevision(private val canal: Canal) : ObserverPrograma {

    override fun ejecutar(programa: Programa) {
        canal.sincListRevision()
    }

}