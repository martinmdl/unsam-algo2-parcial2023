@file:Suppress("SpellCheckingInspection")

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class EquipoSofisticadoSpec : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    describe("Dado un equipo sofisticado") {

        val pipo = Dj(
            100.0,
            LocalDate.of(2023, 1, 27),
            true
        )

        val marto = Dj(
            100.0,
            LocalDate.of(2015, 1, 27),
            true
        )

        val equipo = EquipoBuilder(EquipoDecorado(100.0))
            .sofisticado(3)
            .build()

        it("Dj con suficiente experiencia puede alquilar") {
            equipo.alquilarA(marto)
            equipo.alquilado() shouldBe true
        }

        it("Dj no puede alquilar si no tiene suficiente experiencia") {
            val e = assertThrows<BusinessException> { equipo.alquilarA(pipo) }
            "El DJ no tiene suficiente experiencia" shouldBe e.message
        }
    }
})