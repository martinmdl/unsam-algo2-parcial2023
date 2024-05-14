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
            100.0,
            LocalDate.of(2023, 1, 27),
            false
        )

        val marto = Dj(
            200.0,
            LocalDate.of(2023, 1, 27),
            false
        )

        val equipo1 = EquipoBuilder(EquipoDecorado(100.0))
            .registro()
            .build()

        val equipo2 = EquipoBuilder(EquipoDecorado(100.0))
            .registro()
            .build()

        val equipo3 = EquipoBuilder(EquipoDecorado(300.0))
            .registro()
            .build()

        it("Un Dj alquila por primera vez") {
            RegistroGlobal.getRegistro(pipo) shouldBe 0
            equipo1.alquilarA(pipo)
            equipo1.alquilado() shouldBe true
            RegistroGlobal.getRegistro(pipo) shouldBe 1

        }

        it("Un Dj alquila 2 equipos") {
            RegistroGlobal.getRegistro(marto) shouldBe 0
            equipo1.alquilarA(marto)
            equipo2.alquilarA(marto)
            equipo1.alquilado() shouldBe true
            equipo2.alquilado() shouldBe true
            RegistroGlobal.getRegistro(marto) shouldBe 2
        }

        it("Un Dj quizo alquilar y no pudo, no se registrará") {
            assertThrows<BusinessException> { equipo3.alquilarA(marto) }
            RegistroGlobal.getRegistro(marto) shouldBe 0
        }
    }
})