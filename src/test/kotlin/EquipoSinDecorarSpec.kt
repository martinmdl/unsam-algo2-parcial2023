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

        it("El equipo es alquilado") {
            equipo.alquilarA(marto)
            equipo.alquilado() shouldBe true
        }
    }
})