@file:Suppress("SpellCheckingInspection")

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class EquipoDedicacionSpec : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    describe("Dado un equipo para dedicación plena") {

        val pipo = Dj(
            100.0,
            LocalDate.of(2023, 1, 27),
            false
        )

        val marto = Dj(
            100.0,
            LocalDate.of(2015, 1, 27),
            true
        )

        val equipo = EquipoBuilder(EquipoDecorado(100.0))
            .dedicacionPlena()
            .build()

        it("Dj con dedicación plena, puede alquilar") {
            equipo.alquilarA(marto)
            equipo.alquilado() shouldBe true
        }

        it("Dj no puede alquilar si no tiene dedicación plena") {
            val e = assertThrows<BusinessException> { equipo.alquilarA(pipo) }
            "El DJ no tiene dedicación plena" shouldBe e.message
            equipo.alquilado() shouldBe false
        }
    }
})