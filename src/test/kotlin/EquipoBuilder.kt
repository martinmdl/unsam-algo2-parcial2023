@file:Suppress("SpellCheckingInspection")

class EquipoBuilder(private var equipo: Equipo) {

    fun dedicacionPlena(): EquipoBuilder {
        equipo = EquipoDedicacionPlena(equipo)
        return this
    }

    fun reintegro(coefReintegro: Double): EquipoBuilder {
        equipo = EquipoReintegro(coefReintegro, equipo)
        return this
    }

    fun sofisticado(expRequerida: Int): EquipoBuilder {
        equipo = EquipoSofisticado(expRequerida, equipo)
        return this
    }

    fun registro(): EquipoBuilder {
        equipo = EquipoConRegistro(equipo)
        return this
    }

    fun build(): Equipo {
        if (equipo.costoAlquiler() < 0 && !equipo.alquilado()) {
            throw BusinessException("El costo tiene que ser mayor a cero y/o no estar alquilado")
        }
        return equipo
    }
}