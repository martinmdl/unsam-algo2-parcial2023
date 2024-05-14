@file:Suppress("SpellCheckingInspection")

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class EquipoReintegroSpec : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    describe("Dado un equipo sin decorar") {

        val pipo = Dj(
            10.0,
            LocalDate.of(2015, 1, 27),
            true
        )

        val marto = Dj(
            100.0,
            LocalDate.of(2015, 1, 27),
            true
        )

        val equipo = EquipoBuilder(EquipoDecorado(10000.0, false))
            .reintegro(0.1) // reintegra 10%
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

        it("El equipo es alquilado") {
            pipo.aumentarSaldo(100.0)
            equipo.alquilarA(pipo)
            equipo.alquilado() shouldBe true
        }
    }
})

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
