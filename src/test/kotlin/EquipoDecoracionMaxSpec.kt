@file:Suppress("SpellCheckingInspection")

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class EquipoDecoracionMaxSpec : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    describe("Dado un equipo con todos los decoradores") {

        val pipo = Dj(
            50.0,
            LocalDate.of(2023, 1, 27),
            false
        )

        val marto = Dj(
            200.0,
            LocalDate.of(2015, 1, 27),
            true
        )

        val equipo = EquipoBuilder(EquipoDecorado(200.0))
            .dedicacionPlena()
            .reintegro(0.5)
            .sofisticado(3)
            .registro()
            .build()

        it("Dj no puede alquilar por no tener dedicación plena, ni suficiente saldo, ni ser sofisticado") {

            val e = assertThrows<BusinessException> { equipo.alquilarA(pipo) }
            println(e.message)

            pipo.aumentarSaldo(200.0)
            assertThrows<BusinessException> { equipo.alquilarA(pipo) }

            RegistroGlobal.getRegistro(pipo) shouldBe 0
            pipo.saldo shouldBe 250
        }

        it("Dj puede alquilar ya que cumple todas las condiciones, esto le otorga un reintegro y se registra") {

            RegistroGlobal.getRegistro(marto) shouldBe 0
            equipo.alquilarA(marto)

            marto.saldo shouldBe 100
            RegistroGlobal.getRegistro(marto) shouldBe 1
        }
    }
})