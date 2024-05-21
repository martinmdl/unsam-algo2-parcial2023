@file:Suppress("SpellCheckingInspection")

import ar.edu.unsam.algo2.musicar.BusinessException
import ar.edu.unsam.algo2.musicar.Dj
import ar.edu.unsam.algo2.musicar.EquipoDecorado
import ar.edu.unsam.algo2.musicar.RegistroGlobal
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
            300.0,
            LocalDate.of(2023, 1, 27),
            false
        )

        val equipo1 = EquipoBuilder(EquipoDecorado(100.0))
            .registro()
            .build()

        val equipo2 = EquipoBuilder(EquipoDecorado(200.0))
            .registro()
            .build()

        it("No existe registro de un Dj que nunca alquilo") {
            RegistroGlobal.getRegistro(pipo) shouldBe 0
        }

        it("El registro de un Dj que alquilo un equipo por primera vez") {
            equipo1.alquilarA(pipo)
            RegistroGlobal.getRegistro(pipo) shouldBe 1
        }

        it("El registro de un Dj que alquilo 2 equipos") {
            equipo1.alquilarA(marto)
            equipo2.alquilarA(marto)
            RegistroGlobal.getRegistro(marto) shouldBe 2
        }

        it("Un registro de un Dj que no pudo alquilar") {
            assertThrows<BusinessException> { equipo2.alquilarA(pipo) }
            RegistroGlobal.getRegistro(marto) shouldBe 0
        }
    }
})