@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.musicar

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
    private var estaAlquilado: Boolean = false
) : Equipo {

    override fun validarAlquiler(dj: Dj) {
        if(estaAlquilado) throw BusinessException("Ya está alquilado")
        if (!dj.puedeAlquilar(this)) throw BusinessException("No tiene suficiente saldo")
    }

    override fun alquilarA(dj: Dj) {
        validarAlquiler(dj)
        estaAlquilado = true
        dj.saldo -= this.costoAlquiler()
    }

    override fun alquilado(): Boolean = estaAlquilado

    override fun costoAlquiler(): Double = costoAlquiler

    override fun establecerCosto(costo: Double) { costoAlquiler = costo }
}

// BASE DECORATOR
abstract class EquipoDecoradorBase(val equipo: Equipo) : Equipo {

    override fun validarAlquiler(dj: Dj) = equipo.validarAlquiler(dj)

    override fun alquilado(): Boolean = equipo.alquilado()

    override fun costoAlquiler(): Double = equipo.costoAlquiler()

    override fun establecerCosto(costo: Double) = equipo.establecerCosto(costo)
}

// CONCRETE DECORATORS
class EquipoDedicacionPlena(equipo: Equipo) : EquipoDecoradorBase(equipo) {

    override fun alquilarA(dj: Dj) {
        if(!dj.dedicacionPlena) throw BusinessException("El DJ no tiene dedicación plena")
        equipo.alquilarA(dj)
    }
}

class EquipoReintegro(private val porcentajeReintegro: Double, equipo: Equipo) : EquipoDecoradorBase(equipo) {

    override fun alquilarA(dj: Dj) {
        dj.aumentarSaldo(reintegro())
        equipo.alquilarA(dj)
    }

    private fun reintegro(): Double = equipo.costoAlquiler() * porcentajeReintegro
}

class EquipoSofisticado(private val expRequerida: Int, equipo: Equipo) : EquipoDecoradorBase(equipo) {

    override fun alquilarA(dj: Dj) {
        if(!expSuficiente(dj)) throw BusinessException("El DJ no tiene suficiente experiencia")
        equipo.alquilarA(dj)
    }

    private fun expSuficiente(dj: Dj): Boolean = dj.aniosDeExperiencia() >= expRequerida
}

class EquipoConRegistro(equipo: Equipo) : EquipoDecoradorBase(equipo) {

    override fun alquilarA(dj: Dj) {
        equipo.alquilarA(dj)
        RegistroGlobal.registrar(dj)
    }
}