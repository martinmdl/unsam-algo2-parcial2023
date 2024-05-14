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

        it("Un equipo es alquilado por un Dj con suficiente experiencia") {
            equipo.alquilarA(marto)
            equipo.alquilado() shouldBe true
        }

        it("Un equipo no puede es alquilado por un Dj con suficiente experiencia") {
            val e = assertThrows<BusinessException> { equipo.alquilarA(pipo) }
            "El DJ no tiene suficiente experiencia" shouldBe e.message
        }

        it("Un equipo no es alquilado por un Dj con suficiente experiencia") {
            assertThrows<BusinessException> { equipo.alquilarA(pipo) }
            equipo.alquilado() shouldBe false
        }
    }
})