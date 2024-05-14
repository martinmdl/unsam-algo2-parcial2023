@file:Suppress("SpellCheckingInspection")

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class RegistroGlobalSpec : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf


    describe("Dado el objeto RegistroGlobal") {

    val marto = Dj(
        200.0,
        LocalDate.of(2015, 1, 27),
        true
    )

        it("Si se consulta la clave de un Dj que todavía no existe en el registro, este se creará con el valor 0") {
           RegistroGlobal.getRegistro(marto) shouldBe 0
        }

        it("Se agrega una vez al registro un Dj") {
            RegistroGlobal.registrar(marto)
            RegistroGlobal.getRegistro(marto) shouldBe 1
        }

        it("Se agrega dos veces al registro un Dj") {
            RegistroGlobal.registrar(marto)
            RegistroGlobal.registrar(marto)
            RegistroGlobal.getRegistro(marto) shouldBe 2
        }
    }
})
