@file:Suppress("SpellCheckingInspection")

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class EquipoSinDecorarSpec : DescribeSpec({
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

        val equipo = EquipoBuilder(EquipoDecorado(100.0))
            .build()

        it("Dj no puede alquilar si no tiene suficiente saldo") {
            val e = assertThrows<BusinessException> { equipo.alquilarA(pipo) }
            "No tiene suficiente saldo" shouldBe e.message
            equipo.alquilado() shouldBe false
        }

        it("Dj no puede alquilar un equipo ocupado") {
            equipo.alquilarA(marto)
            val e = assertThrows<BusinessException> { equipo.alquilarA(pipo) }
            "Ya está alquilado" shouldBe e.message
        }

        it("El equipo es alquilado") {
            equipo.alquilarA(marto)
            equipo.alquilado() shouldBe true
        }
    }
})