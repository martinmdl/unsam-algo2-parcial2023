@file:Suppress("SpellCheckingInspection")

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class EquipoReintegroSpec : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    describe("Dado un equipo con reintegro") {

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

        val equipo = EquipoBuilder(EquipoDecorado(100.0))
            .reintegro(0.1)
            .build()

        it("Un equipo es alquilado y reintegra el 10% de su valor") {
            equipo.alquilarA(marto)
            marto.saldo shouldBe 10
        }

        it("Un equipo no reintegra si no puede ser alquilado") {
            assertThrows<BusinessException> { equipo.alquilarA(pipo) }
            pipo.saldo shouldBe 10
        }
    }
})
