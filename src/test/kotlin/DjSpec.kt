import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class DjSpec : DescribeSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    describe("Dado") {

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

        it("Dj puede aumentar su saldo") {
            pipo.aumentarSaldo(100.0)
            pipo.saldo shouldBe 100.0 + 100.0
        }

        it("Dj que empezó a trabajar en 2023 tiene 1 año de experiencia") {
            pipo.aniosDeExperiencia() shouldBe 1
        }

        it("Dj que empezó a trabajar en 2015 tiene 9 año de experiencia") {
            marto.aniosDeExperiencia() shouldBe 9
        }
    }
})