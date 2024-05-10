@file:Suppress("SpellCheckingInspection")

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

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

class EquipoSpec : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    describe("Dado un equipo sin decorar") {

        val pipo = Dj(
            10.0,
            LocalDate.of(2015,1,27),
            true
        )
        val marto = Dj(
            100.0,
            LocalDate.of(2015,1,27),
            true
        )

        val equipo = EquipoBuilder(EquipoDecorado(100.0, false))
            .build()

        it("Habrá una excepción si el Dj tiene saldo insuficiente") {
            val e = assertThrows<BusinessException> { equipo.alquilarA(pipo) }
            "No tiene suficiente saldo" shouldBe e.message
        }

        it("Equipo no es alquilado por un Dj (ya esta alquilado)") {
            equipo.alquilarA(marto)
            val e = assertThrows<BusinessException> { equipo.alquilarA(pipo) }
            "Ya está alquilado" shouldBe e.message
        }
        it("El equipo es alquilado"){
            pipo.aumentarSaldo(100.0)
            equipo.alquilarA(pipo)
            equipo.alquilado() shouldBe true
        }
    }

//    describe("Dado un equipo") {
//
//        val equipo = EquipoBuilder(EquipoDecorado(10000.0, false))
//            .dedicacionPlena()
//            .reintegro(0.1)
//            .sofisticado(3)
//            .registro()
//            .build()
//
//        it("tu hermana") {
//
//        }
})