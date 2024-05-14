@file:Suppress("SpellCheckingInspection")

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class EquipoSinDecorarSpec : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    describe("Dado un equipo sin decorar") {

        val pipo = Dj(
            10.0,
            LocalDate.of(2015,1,27),
            true
        )

        val marto = Dj(
            100.0,
            LocalDate.of(2015,1,27),
            true
        )

        val equipo = EquipoBuilder(EquipoDecorado(100.0))
            .build()

        it("Un equipo puede establecer un costo nuevo"){
            equipo.establecerCosto(300.0)
            equipo.costoAlquiler() shouldBe 300
        }

        it("Un equipo es alquilado") {
            equipo.alquilarA(marto)
            equipo.alquilado() shouldBe true
        }

        it("Un equipo ocupado no puede ser alquilado por un Dj") {
            equipo.alquilarA(marto)
            val e = assertThrows<BusinessException> { equipo.alquilarA(pipo) }
            "Ya está alquilado" shouldBe e.message
        }

        it("Un equipo no puede ser alquilado por un Dj sin saldo suficiente") {
            val e = assertThrows<BusinessException> { equipo.alquilarA(pipo) }
            "No tiene suficiente saldo" shouldBe e.message
        }

        it("Un equipo no es alquilado por un Dj sin saldo suficiente") {
            assertThrows<BusinessException> { equipo.alquilarA(pipo) }
            equipo.alquilado() shouldBe false
        }
    }
})