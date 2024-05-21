@file:Suppress("SpellCheckingInspection")

import ar.edu.unsam.algo2.musicar.BusinessException
import ar.edu.unsam.algo2.musicar.Dj
import ar.edu.unsam.algo2.musicar.EquipoDecorado
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

        it("Un equipo puede ser alquilado por un Dj con dedicacion plena") {
            equipo.alquilarA(marto)
            equipo.alquilado() shouldBe true
        }

        it("Un equipo no puede ser alquilado por un Dj sin dedicacion plena") {
            val e = assertThrows<BusinessException> { equipo.alquilarA(pipo) }
            "El DJ no tiene dedicación plena" shouldBe e.message
        }

        it("Un equipo no puede ser alquilado por un Dj sin dedicacion plena") {
            assertThrows<BusinessException> { equipo.alquilarA(pipo) }
            equipo.alquilado() shouldBe false
        }
    }
})