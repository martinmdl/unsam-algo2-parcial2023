@file:Suppress("SpellCheckingInspection")

import ar.edu.unsam.algo2.musicar.*
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows

class EquipoBuilder(private var equipo: Equipo = EquipoDecorado(200.0)) {

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
        if (equipo.costoAlquiler() < 0) throw BusinessException("El costo tiene que ser mayor a cero")
        if (equipo.alquilado()) throw BusinessException("El equipo no debe estar alquilado")
        return equipo
    }
}

class EquipoBuilderSpec : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    describe("Dada una instanciación hecha con el builder") {

        it("Un equipo no puede ser creado con costo negativo") {
            val e = assertThrows<BusinessException> { EquipoBuilder(EquipoDecorado(-1.0)).build() }
            "El costo tiene que ser mayor a cero" shouldBe e.message
        }

        it("Un equipo no puede ser creado en estado alquilado") {
            val e = assertThrows<BusinessException> { EquipoBuilder(EquipoDecorado(1.0, true)).build() }
            "El equipo no debe estar alquilado" shouldBe e.message
        }
    }
})
