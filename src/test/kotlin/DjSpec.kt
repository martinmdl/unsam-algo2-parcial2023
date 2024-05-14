@file:Suppress("SpellCheckingInspection")

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class DjSpec : DescribeSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    describe("Dado") {

        val pipo = Dj(
            100.0,
            LocalDate.of(2023, 1, 27),
            true
        )

        val marto = Dj(
            200.0,
            LocalDate.of(2015, 1, 27),
            true
        )

        val equipo = EquipoBuilder(EquipoDecorado(200.0)).build()

        it("Dj puede aumentar su saldo") {
            pipo.aumentarSaldo(100.0)
            pipo.saldo shouldBe 200
        }

        it("Dj que empezó a trabajar en 2023 tiene 1 año de experiencia") {
            pipo.aniosDeExperiencia() shouldBe 1
        }

        it("Dj que empezó a trabajar en 2015 tiene 9 año de experiencia") {
            marto.aniosDeExperiencia() shouldBe 9
        }

        it("Dj puede alquilar, paga el costo de alquiler"){
            marto.alquilar(equipo)
            marto.saldo shouldBe 0
        }

        it("Dj no puede alquilar, mantiene su saldo"){
            assertThrows<BusinessException> { pipo.alquilar(equipo) }
            pipo.saldo shouldBe 100
        }
    }
})