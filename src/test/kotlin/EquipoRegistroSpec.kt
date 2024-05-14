@file:Suppress("SpellCheckingInspection")

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class EquipoRegistroSpec : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    describe("Dado un equipo con registro de alquiler") {

        val pipo = Dj(
            1000.0,
            LocalDate.of(2023, 1, 27),
            false
        )

        val equipo = EquipoBuilder(EquipoDecorado(100.0))
            .registro()
            .build()

        it("Dj alquila por primera vez") {
            equipo.alquilarA(pipo)
            equipo.alquilado() shouldBe true
            equipo.registro() shouldBe 1
        }

        it("Dj no puede alquilar si no tiene dedicación plena") {
            val e = assertThrows<BusinessException> { equipo.alquilarA(pipo) }
            "El DJ no tiene dedicación plena" shouldBe e.message
            equipo.alquilado() shouldBe false
        }
    }
})