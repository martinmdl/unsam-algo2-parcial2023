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
    private var estaAlquilado: Boolean = false
) : Equipo {

    override fun validarAlquiler(dj: Dj) { if(!estaAlquilado) throw BusinessException("Ya está alquilado") }

    override fun alquilarA(dj: Dj) {
        validarAlquiler(dj)
        dj.alquilar(this)
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
class EquipoDedicacionPlena(equipo: Equipo) : EquipoDecoradorBase(equipo) {

    override fun validarAlquiler(dj: Dj) {
        if(!dj.dedicacionPlena) throw BusinessException("El DJ no tiene dedicación plena")
        equipo.validarAlquiler(dj)
    }
}

class EquipoReintegro(private val coefReintegro: Double, equipo: Equipo) : EquipoDecoradorBase(equipo) {

    companion object { const val ENTERO = 1 }
    override fun costoAlquiler(): Double = equipo.costoAlquiler() * (ENTERO - coefReintegro)
}

class EquipoSofisticado(private val expRequerida: Int, equipo: Equipo) : EquipoDecoradorBase(equipo) {

    override fun validarAlquiler(dj: Dj) {
        if(!expSuficiente(dj)) throw BusinessException("El DJ no tiene suficiente experiencia")
        equipo.validarAlquiler(dj)
    }

    private fun expSuficiente(dj: Dj): Boolean = dj.aniosDeExperiencia() <= expRequerida
}

class EquipoConRegistro(equipo: Equipo) : EquipoDecoradorBase(equipo) {

    private val registro: MutableMap<Dj, Int> = mutableMapOf()

    override fun alquilarA(dj: Dj) {
        equipo.alquilarA(dj)
        registro[dj] = registro.getOrPut(dj) { 0 } + 1
    }
}