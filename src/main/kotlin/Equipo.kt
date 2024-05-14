@file:Suppress("SpellCheckingInspection")

// COMPONENT
interface Equipo {
    fun validarAlquiler(dj: Dj)
    fun alquilarA(dj: Dj)
    fun alquilado(): Boolean
    fun costoAlquiler(): Double
    fun establecerCosto(costo: Double)
}

// CONCRETE COMPONENT
class EquipoDecorado (
    private var costoAlquiler: Double,
    var estaAlquilado: Boolean = false
) : Equipo {

    override fun validarAlquiler(dj: Dj) {
        if(estaAlquilado) throw BusinessException("Ya está alquilado")
        dj.alquilar(this)
    }

    override fun alquilarA(dj: Dj) {
        validarAlquiler(dj)
        estaAlquilado = true
    }

    override fun alquilado(): Boolean = estaAlquilado

    override fun costoAlquiler(): Double = costoAlquiler

    override fun establecerCosto(costo: Double) { costoAlquiler = costo }
}

// BASE DECORATOR
abstract class EquipoDecoradorBase(val equipo: Equipo) : Equipo {

    override fun validarAlquiler(dj: Dj) = equipo.validarAlquiler(dj)

    override fun alquilarA(dj: Dj) = equipo.alquilarA(dj)

    override fun alquilado(): Boolean = equipo.alquilado()

    override fun costoAlquiler(): Double = equipo.costoAlquiler()

    override fun establecerCosto(costo: Double) = equipo.establecerCosto(costo)
}

// CONCRETE DECORATORS

// Problema: al modificar validarAlquiler() nunca lanza la excepción.
// - Corroboré orden de los métodos -> estaban ordenados y seguía el problema
// - Cambié la delegación de "equipo" a "super" -> seguía el problema
// Corrección: modifiqué alquilarA() y se arregló
class EquipoDedicacionPlena(equipo: Equipo) : EquipoDecoradorBase(equipo) {

    override fun alquilarA(dj: Dj) {
        if(!dj.dedicacionPlena) throw BusinessException("El DJ no tiene dedicación plena")
        equipo.alquilarA(dj)
    }
}

// Problema: https://groups.google.com/g/algo2-unsam/c/_Ii1b0jcgE0
// Corrección: no modificar costoAlquiler(), sino alquilarA()
class EquipoReintegro(private val porcentajeReintegro: Double, equipo: Equipo) : EquipoDecoradorBase(equipo) {

    override fun alquilarA(dj: Dj) {
        equipo.alquilarA(dj)
        dj.aumentarSaldo(reintegro())
    }

    private fun reintegro(): Double = equipo.costoAlquiler() * porcentajeReintegro
}

// Problema: al modificar validarAlquiler() nunca lanza la excepción.
// - Corroboré orden de los métodos -> estaban ordenados y seguía el problema
// - Probé dj.aniosDeExperiencia() en DjSpec.kt -> funcionaba y seguía el problema
// Corrección: modifiqué alquilarA() y se arregló
class EquipoSofisticado(private val expRequerida: Int, equipo: Equipo) : EquipoDecoradorBase(equipo) {

    override fun alquilarA(dj: Dj) {
        if(!expSuficiente(dj)) throw BusinessException("El DJ no tiene suficiente experiencia")
        equipo.alquilarA(dj)
    }

    private fun expSuficiente(dj: Dj): Boolean = dj.aniosDeExperiencia() >= expRequerida
}


class EquipoConRegistro(equipo: Equipo) : EquipoDecoradorBase(equipo) {

//    private val registro: MutableMap<Dj, Int> = mutableMapOf()

    override fun alquilarA(dj: Dj) {
        equipo.alquilarA(dj)
        RegistroGlobal.registrar(dj)
    }
}