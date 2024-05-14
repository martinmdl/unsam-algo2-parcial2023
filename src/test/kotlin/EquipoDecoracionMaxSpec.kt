@file:Suppress("SpellCheckingInspection")

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class EquipoDecoracionMaxSpec : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    describe("Dado un equipo con todos los decoradores") {

        val marto = Dj(
            200.0,
            LocalDate.of(2015, 1, 27),
            true
        )

        val cris = Dj(
            100.0,
            LocalDate.of(2015, 1, 27),
            true
        )

        val pipo = Dj(
            200.0,
            LocalDate.of(2015, 1, 27),
            false
        )

        val guille = Dj(
            200.0,
            LocalDate.of(2024, 1, 27),
            true
        )

        val equipo = EquipoBuilder(EquipoDecorado(200.0))
            .dedicacionPlena()
            .sofisticado(3)
            .reintegro(0.5)
            .registro()
            .build()

        val equipoDistinto = EquipoBuilder(EquipoDecorado(200.0))
            .registro()
            .reintegro(0.5)
            .sofisticado(3)
            .dedicacionPlena()
            .build()

        it("Un equipo no puede ser alquilado por un Dj sin saldo suficiente"){
            val e = assertThrows<BusinessException> { equipo.alquilarA(cris) }
            "No tiene suficiente saldo" shouldBe e.message
        }

        it("Un equipo no puede ser alquilado por un Dj sin dedicacion plena"){
            val e = assertThrows<BusinessException> { equipo.alquilarA(pipo) }
            "El DJ no tiene dedicación plena" shouldBe e.message
        }

        it("Un equipo no puede ser alquilado por un Dj sin experiencia suficiente"){
            val e = assertThrows<BusinessException> { equipo.alquilarA(guille) }
            "El DJ no tiene suficiente experiencia" shouldBe e.message
        }

        it("Un equipo distinto no puede ser alquilado por un Dj sin saldo suficiente"){
            val e = assertThrows<BusinessException> { equipoDistinto.alquilarA(cris) }
            "No tiene suficiente saldo" shouldBe e.message
        }

        it("Un equipo distinto no puede ser alquilado por un Dj sin dedicacion plena"){
            val e = assertThrows<BusinessException> { equipoDistinto.alquilarA(pipo) }
            "El DJ no tiene dedicación plena" shouldBe e.message
        }

        it("Un equipo distinto no puede ser alquilado por un Dj sin experiencia suficiente"){
            val e = assertThrows<BusinessException> { equipoDistinto.alquilarA(guille) }
            "El DJ no tiene suficiente experiencia" shouldBe e.message
        }

        it("Un equipo puede ser alquilado") {
            equipo.alquilarA(marto)
            equipo.alquilado() shouldBe true
        }

        it("Un equipo puede ser alquilado, recive reintegro") {
            equipo.alquilarA(marto)
            marto.saldo shouldBe 100
        }

        it("Un equipo puede ser alquilado, aumenta su registro") {
            equipo.alquilarA(marto)
            RegistroGlobal.getRegistro(marto) shouldBe 1
        }
    }
})